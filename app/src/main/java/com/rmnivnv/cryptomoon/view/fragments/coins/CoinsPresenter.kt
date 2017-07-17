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
        getPrices()
    }

    private fun getPrices() {
        val queryMap: HashMap<String, ArrayList<String>> = HashMap()
        val fromList: ArrayList<String> = ArrayList()
        fromList.add(BTC)
        fromList.add(ETH)
        fromList.add(SNT)
        val toList: ArrayList<String> = ArrayList()
        toList.add(USD)
        queryMap.put(FSYMS, fromList)
        queryMap.put(TSYMS, toList)
        disposable.add(networkManager.getPrice(queryMap, object : GetPriceCallback {
            override fun onSuccess(coinsInfoList: ArrayList<PriceBodyDisplay>) {
                coinsInfoList.forEach { Log.d("onSuccess", it.toString()) }
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
            }
        }))
    }

    override fun onDestroy() {
        disposable.clear()
    }
}