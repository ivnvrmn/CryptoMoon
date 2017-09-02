package com.rmnivnv.cryptomoon.view.topCoins

import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.GetTopCoinsCallback
import com.rmnivnv.cryptomoon.model.TopCoinData
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.network.NetworkRequests
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 02/09/2017.
 */
class TopCoinsPresenter : ITopCoins.Presenter {

    @Inject lateinit var view: ITopCoins.View
    @Inject lateinit var db: CMDatabase
    @Inject lateinit var networkRequests: NetworkRequests
    @Inject lateinit var coinsController: CoinsController

    private val disposable = CompositeDisposable()
    private var coins: ArrayList<TopCoinData> = ArrayList()
    private var isRefreshing = false

    override fun onCreate(component: TopCoinsComponent, coins: ArrayList<TopCoinData>) {
        component.inject(this)
        this.coins = coins
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        disposable.add(db.topCoinsDao().getAllTopCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsUpdated(it) }))
    }

    private fun onCoinsUpdated(list: List<TopCoinData>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            view.updateRecyclerView()
        }
    }

    override fun onStart() {
        updateTopCoins()
    }

    private fun updateTopCoins() {
        disposable.add(networkRequests.getTopCoins(object : GetTopCoinsCallback {
            override fun onSuccess(coins: List<TopCoinData>) {
                if (coins.isNotEmpty()) {
                    coinsController.saveTopCoinsList(coins)
                    if (isRefreshing) {
                        view.hideRefreshing()
                        isRefreshing = false
                    }
                }
            }

            override fun onError(t: Throwable) {

            }
        }))
    }

    override fun onCoinClicked(coin: TopCoinData) {
    }

    override fun onSwipeUpdate() {
        isRefreshing = true
        updateTopCoins()
    }
}