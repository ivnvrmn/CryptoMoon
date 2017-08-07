package com.rmnivnv.cryptomoon.view.coins

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.DBController
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.network.NetworkRequests
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsPresenter : ICoins.Presenter {

    @Inject lateinit var app: MainApp
    @Inject lateinit var view: ICoins.View
    @Inject lateinit var networkRequests: NetworkRequests
    @Inject lateinit var preferencesProvider: PreferencesProvider
    @Inject lateinit var dbController: DBController
    @Inject lateinit var coinsController: CoinsController

    private val disposable = CompositeDisposable()
    private lateinit var coins: ArrayList<CoinBodyDisplay>
    private var allCoinsInfo: ArrayList<Coin> = ArrayList()
    private var isRefreshing = false

    override fun onCreate(component: CoinsComponent) {
        component.inject(this)
    }

    override fun onViewCreated(coins: ArrayList<CoinBodyDisplay>) {
        this.coins = coins
        displayCoins()
    }

    private fun displayCoins() {
        coinsController.getCoinsToDisplay(object : GetDisplayCoinsCallback {
            override fun onSuccess(list: List<CoinBodyDisplay>) {
                coins.clear()
                coins.addAll(list)
                coins.sortBy { it.from }
                view.updateRecyclerView()
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
            }
        })
    }

    override fun onStart() {
        updateCoins()
    }

    private fun updateCoins() {
        view.disableSwipeToRefresh()
        RxBus.publish(CoinsLoadingEvent(true))
        val spCoinsSize = preferencesProvider.getSelectedCoins()[FSYMS]?.size
        if (spCoinsSize != null && (spCoinsSize > coins.size || isNeedToUpdateImgUrls())) {
            getAllCoinsInfo()
        } else {
            getPrices()
        }
    }

    private fun isNeedToUpdateImgUrls(): Boolean {
        coins.forEach {
            if (it.imgUrl.isNullOrEmpty()) {
                return true
            }
        }
        return false
    }

    private fun getAllCoinsInfo() {
        disposable.add(networkRequests.getAllCoins(object : GetAllCoinsCallback {
            override fun onSuccess(allCoins: ArrayList<Coin>) {
                allCoinsInfo = allCoins
                getPrices()
                saveAllCoinsToDb()
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
                RxBus.publish(CoinsLoadingEvent(false))
            }
        }))
    }

    private fun saveAllCoinsToDb() {
        val list = allCoinsInfo.toList()
        if (list.isNotEmpty()) {
            dbController.saveAllCoins(list)
        }
    }

    private fun getPrices() {
        disposable.add(networkRequests.getPrice(preferencesProvider.getSelectedCoins(), object : GetPriceCallback {
            override fun onSuccess(coinsInfoList: ArrayList<CoinBodyDisplay>?) {
                checkIsRefreshing()
                RxBus.publish(CoinsLoadingEvent(false))
                if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                    coins.clear()
                    coins.addAll(coinsInfoList)
                    coins.forEach {
                        val name = it.from
                        it.imgUrl = allCoinsInfo.find { it.name == name }?.imageUrl ?: ""
                    }
                    saveUpdatedCoinsToDb()
                    coins.sortBy { it.from }
                    view.updateRecyclerView()
                }
                view.enableSwipeToRefresh()
            }

            override fun onError(t: Throwable) {
                checkIsRefreshing()
                RxBus.publish(CoinsLoadingEvent(false))
                view.enableSwipeToRefresh()
                Log.d("onError", t.message)
            }
        }))
    }

    private fun checkIsRefreshing() {
        if (isRefreshing) {
            view.hideRefreshing()
            isRefreshing = false
        }
    }

    private fun saveUpdatedCoinsToDb() {
        var id = 0L
        coins.forEach {
            it.id = id
            dbController.saveDisplayCoin(it)
            id++
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onSwipeUpdate() {
        isRefreshing = true
        RxBus.publish(CoinsLoadingEvent(true))
        getPrices()
    }
}