package com.rmnivnv.cryptomoon.view.coins

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.network.NetworkManager
import io.reactivex.Single
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
    @Inject lateinit var networkManager: NetworkManager
    @Inject lateinit var preferencesManager: PreferencesManager
    @Inject lateinit var db: CMDatabase

    private val disposable = CompositeDisposable()
    private lateinit var coins: ArrayList<CoinBodyDisplay>
    private var allCoinsInfo: ArrayList<Coin>? = null
    private var isRefreshing = false

    override fun onCreate(component: CoinsComponent) {
        component.inject(this)
    }

    override fun onViewCreated(coins: ArrayList<CoinBodyDisplay>) {
        this.coins = coins
        setCoinsFromDb()
    }

    private fun setCoinsFromDb() {
        disposable.add(db.displayCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    if (it.isNotEmpty()) {
                        showCoinsFromDb(it)
                    }
                }))
    }

    private fun showCoinsFromDb(list: List<CoinBodyDisplay>) {
        coins.clear()
        coins.addAll(list)
        coins.sortBy { it.from }
        view.updateRecyclerView()
    }

    override fun onStart() {
        RxBus.publish(CoinsLoadingEvent(true))
        val spCoinsSize = preferencesManager.getSelectedCoins()[FSYMS]?.size
        if (spCoinsSize != null && spCoinsSize > coins.size) {
            getAllCoinsInfo()
        } else {
            getPrices()
        }
    }

    private fun getAllCoinsInfo() {
        disposable.add(networkManager.getAllCoins(object : GetAllCoinsCallback {
            override fun onSuccess(allCoins: ArrayList<Coin>) {
                allCoinsInfo = allCoins
                getPrices()
            }

            override fun onError(t: Throwable) {
                Log.d("onError", t.message)
                RxBus.publish(CoinsLoadingEvent(false))
            }
        }))
    }

    private fun getPrices() {
//        val queryMap: HashMap<String, ArrayList<String>> = HashMap()
//        val fromList: ArrayList<String> = ArrayList()
//        fromList.add(BTC)
//        fromList.add(ETH)
//        fromList.add(SNT)
//        val toList: ArrayList<String> = ArrayList()
//        toList.add(USD)
//        queryMap.put(FSYMS, fromList)
//        queryMap.put(TSYMS, toList)
//        println("This is querymap json: " + Gson().toJson(queryMap))



        disposable.add(networkManager.getPrice(preferencesManager.getSelectedCoins(), object : GetPriceCallback {
            override fun onSuccess(coinsInfoList: ArrayList<CoinBodyDisplay>?) {
                checkIsRefreshing()
                RxBus.publish(CoinsLoadingEvent(false))
                if (coinsInfoList != null && coinsInfoList.size > 0) {
                    coins.clear()
                    coins.addAll(coinsInfoList)
                    coins.forEach {
                        val name = it.from
                        it.imgUrl = allCoinsInfo?.find { it.name == name }?.imageUrl
                    }
                    saveUpdatedCoinsToDb()
                    coins.sortBy { it.from }
                    view.updateRecyclerView()
                }
            }

            override fun onError(t: Throwable) {
                checkIsRefreshing()
                RxBus.publish(CoinsLoadingEvent(false))
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
            saveCoinToDb(it)
            id++
        }
    }

    private fun saveCoinToDb(coin: CoinBodyDisplay) {
        disposable.add(Single.fromCallable { db.displayCoinsDao().insert(coin) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun updateCoins() {
        isRefreshing = true
        RxBus.publish(CoinsLoadingEvent(true))
        getPrices()
    }
}