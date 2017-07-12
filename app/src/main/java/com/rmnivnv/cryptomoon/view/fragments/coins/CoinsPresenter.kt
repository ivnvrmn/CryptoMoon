package com.rmnivnv.cryptomoon.view.fragments.coins

import android.util.Log
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.network.NetworkManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsPresenter : ICoins.Presenter {

    @Inject lateinit var view: ICoins.View
    @Inject lateinit var networkManager: NetworkManager

    private val disposable = CompositeDisposable()
    private var coinsList: MutableList<Market> = ArrayList()

    override fun onCreate(component: CoinsComponent) {
        component.inject(this)
    }

    override fun onViewCreated() {
        disposable.add(networkManager.getPrice(ETH, USD, object : GetPriceCallback {
            override fun onSuccess(price: PriceBodyDisplay) {
                Log.d("onSuccess", price.toString())
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.toString())
            }
        }) )
    }

    override fun onDestroy() {
        disposable.clear()
    }
}