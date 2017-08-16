package com.rmnivnv.cryptomoon.view.coins

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.OnDeleteCoinsMenuItemClickedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.createCoinsMapFromList
import com.rmnivnv.cryptomoon.utils.toastShort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsPresenter : ICoins.Presenter {

    @Inject lateinit var app: MainApp
    @Inject lateinit var view: ICoins.View
    @Inject lateinit var networkRequests: NetworkRequests
    @Inject lateinit var coinsController: CoinsController
    @Inject lateinit var db: CMDatabase
    @Inject lateinit var resProvider: ResourceProvider

    private val disposable = CompositeDisposable()
    private lateinit var coins: ArrayList<DisplayCoin>
    private var isRefreshing = false

    override fun onCreate(component: CoinsComponent) {
        component.inject(this)
        addCoinsChangesObservable()
        getAllCoinsInfo()
        setupRxBusEventsListeners()
    }

    private fun addCoinsChangesObservable() {
        disposable.add(db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsFromDbUpdates(it) }))
    }

    private fun onCoinsFromDbUpdates(list: List<DisplayCoin>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            coins.sortBy { it.from }
            view.updateRecyclerView()
        }
    }

    private fun getAllCoinsInfo() {
        disposable.add(networkRequests.getAllCoins(object : GetAllCoinsCallback {
            override fun onSuccess(allCoins: ArrayList<InfoCoin>) {
                if (allCoins.isNotEmpty()) {
                    coinsController.saveAllCoinsInfo(allCoins)
                }
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
            }
        }))
    }

    private fun setupRxBusEventsListeners() {
        disposable.add(RxBus.listen(OnDeleteCoinsMenuItemClickedEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onDeleteClicked()
                })
    }

    private fun onDeleteClicked() {
        val coinsToDelete = coins.filter { it.selected }
        val toast = if (coinsToDelete.size > 1) resProvider.getString(R.string.coins_deleted)
            else resProvider.getString(R.string.coin_deleted)
        coinsController.deleteDisplayCoins(coinsToDelete)
        app.toastShort(toast)
    }

    override fun onViewCreated(coins: ArrayList<DisplayCoin>) {
        this.coins = coins
    }

    override fun onStart() {
        updatePrices()
    }

    private fun updatePrices() {
        val queryMap = createCoinsMapFromList(coins)
        if (queryMap.isNotEmpty()) {
            RxBus.publish(CoinsLoadingEvent(true))
            disposable.add(networkRequests.getPrice(queryMap, object : GetPriceCallback {
                override fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?) {
                    if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                        coinsController.saveDisplayCoinList(coinsInfoList)
                    }
                    afterRefreshing()
                }

                override fun onError(t: Throwable) {
                    afterRefreshing()
                }
            }))
        }
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
        updatePrices()
    }

    override fun onCoinClicked(coin: DisplayCoin) {

    }
}