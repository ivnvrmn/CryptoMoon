package com.rmnivnv.cryptomoon.view.coins

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.*
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
    @Inject lateinit var coinsController: CoinsController

    private val disposable = CompositeDisposable()
    private lateinit var coins: ArrayList<DisplayCoin>
    private var allCoinsInfo: ArrayList<InfoCoin> = ArrayList()
    private var isRefreshing = false

    override fun onCreate(component: CoinsComponent) {
        component.inject(this)
        getAllCoinsInfo()
    }

    private fun getAllCoinsInfo() {
        disposable.add(networkRequests.getAllCoins(object : GetAllCoinsCallback {
            override fun onSuccess(allCoins: ArrayList<InfoCoin>) {
                if (allCoins.isNotEmpty()) {
                    allCoinsInfo = allCoins
                    coinsController.saveAllCoinsInfo(allCoins)
                }
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
            }
        }))
    }

    override fun onViewCreated(coins: ArrayList<DisplayCoin>) {
        this.coins = coins
    }

    override fun onStart() {
        displayCoins()
    }

    private fun displayCoins() {
        coinsController.getCoinsToDisplay(object : GetDisplayCoinsCallback {
            override fun onSuccess(list: List<DisplayCoin>) {
                if (list.isNotEmpty()) {
                    coins.clear()
                    coins.addAll(list)
                    coins.sortBy { it.from }
                    view.updateRecyclerView()
                    //getPrices()
                }
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
            }
        })
    }

    private fun getPrices() {
        //TODO разобраться с этим методом
        coinsController.getDisplayCoinsMap(object : GetDisplayCoinsMapFromDbCallback {
            override fun onSuccess(map: HashMap<String, ArrayList<String>>) {
                if (map.isNotEmpty()) {
                    val fromCoinsListSize = map[FSYMS]?.size
                    if (fromCoinsListSize != null && fromCoinsListSize > 0) {
                        requestPrices(map)
                    } else {
                        afterRefreshing()
                    }
                } else {
                    afterRefreshing()
                }
            }

            override fun onError(t: Throwable) {
                afterRefreshing()
            }
        })
    }

    private fun requestPrices(map: HashMap<String, ArrayList<String>>) {
        disposable.add(networkRequests.getPrice(map, object : GetPriceCallback {
            override fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?) {
                if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                    coins.clear()
                    coins.addAll(coinsInfoList)
                    coins.sortBy { it.from }
                    coinsController.saveDisplayCoinList(coins)
                    view.updateRecyclerView()
                }
                afterRefreshing()
            }

            override fun onError(t: Throwable) {
                afterRefreshing()
            }
        }))
    }

    private fun afterRefreshing() {
        RxBus.publish(CoinsLoadingEvent(false))
        if (isRefreshing) {
            view.hideRefreshing()
            isRefreshing = false
            view.enableSwipeToRefresh()
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

    override fun onCoinClicked(coin: DisplayCoin) {

    }

    override fun onCoinLongClicked(coin: DisplayCoin): Boolean {
        view.showCoinPopMenu(coin)
        return true
    }

    override fun onRemoveCoinClicked(coin: DisplayCoin) {
        //TODO remove coin
//        coinsController.deleteDisplayCoin(coin)
//        coins.remove(coin)
//        view.updateRecyclerView()
    }
}