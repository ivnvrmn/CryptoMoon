package com.rmnivnv.cryptomoon.view.coins.addCoin

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.toastLong
import com.rmnivnv.cryptomoon.utils.toastShort
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rmnivnv on 27/07/2017.
 */
class AddCoinPresenter : IAddCoin.Presenter {

    @Inject lateinit var app: MainApp
    @Inject lateinit var view: IAddCoin.View
    @Inject lateinit var coinsController: CoinsController
    @Inject lateinit var networkRequests: NetworkRequests
    @Inject lateinit var resProvider: ResourceProvider

    private val disposable = CompositeDisposable()
    private var allCoins: List<InfoCoin>? = null
    private lateinit var matches: ArrayList<InfoCoin>
    private var fromCoin: InfoCoin = InfoCoin()

    override fun onCreate(component: AddCoinComponent, matches: ArrayList<InfoCoin>) {
        component.inject(this)
        this.matches = matches
        checkAllCoins()
    }

    private fun checkAllCoins() {
        coinsController.getAllCoinsInfo(object : GetAllCoinsFromDbCallback {
            override fun onSuccess(list: List<InfoCoin>) {
                if (list.isNotEmpty()) {
                    println("checkAllCoins COINS SIZE = " + list.size)
                    allCoins = list
                } else {
                    //todo request all coins info
                }
            }

            override fun onError(t: Throwable) {
                //todo request all coins info
            }
        })
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onFromTextChanged(text: String) {
        view.enableMatchesCount()
        if (text.isNotEmpty()) {
            val matchesList = allCoins?.filter { (it.coinName.contains(text, true)) || (it.name.contains(text, true)) }?.reversed()
            if (matchesList != null && matchesList.isNotEmpty()) {
                view.setMatchesResultSize(matchesList.size.toString())
                updateCoinsList(matchesList)
            } else {
                updateCoinsList(null)
            }
        } else {
            updateCoinsList(null)
        }
    }

    private fun updateCoinsList(list: List<InfoCoin>?) {
        matches.clear()
        if (list != null) {
            matches.addAll(list)
        } else {
            view.setMatchesResultSize("0")
        }
        view.updateRecyclerView()
    }

    override fun onFromItemClicked(coin: InfoCoin) {
        view.disableMatchesCount()
        fromCoin = coin
        matches.clear()
        matches.add(coin)
        view.updateRecyclerView()
        view.hideKeyboard()
        requestCoinInfo()
    }

    private fun requestCoinInfo() {
        view.enableLoadingLayout()
        disposable.add(networkRequests.getPrice(createQueryMap(), object : GetPriceCallback {
            override fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?) {
                if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                    coinsInfoList.forEach {
                        saveCoinToPreferences(it)
                    }
                } else {
                    view.disableLoadingLayout()
                    app.toastShort(resProvider.getString(R.string.coin_not_found))
                }
            }

            override fun onError(t: Throwable) {
                view.disableLoadingLayout()
                Log.d("onError", t.message)
            }
        }))


    }

    private fun createQueryMap(): HashMap<String, ArrayList<String>> {
        val queryMap: HashMap<String, ArrayList<String>> = HashMap()
        val fromList: ArrayList<String> = ArrayList()
        fromList.add(fromCoin.name)
        val toList: ArrayList<String> = ArrayList()
        toList.add(USD)
        queryMap.put(FSYMS, fromList)
        queryMap.put(TSYMS, toList)
        return queryMap
    }

    private fun saveCoinToPreferences(coin: DisplayCoin) {
        val map = coinsController.getDisplayCoinsMap()
        val fsymsArray = map[FSYMS]
        fsymsArray!!.forEach {
            if (it == coin.FROMSYMBOL) {
                view.disableLoadingLayout()
                app.toastShort(resProvider.getString(R.string.coin_already_added))
                return
            }
        }
        fsymsArray.add(coin.FROMSYMBOL)
        map.put(FSYMS, fsymsArray)
        coinsController.setDisplayCoins(map)
        coinSuccessfullyAdded()
    }

    private fun coinSuccessfullyAdded() {
        app.toastLong(resProvider.getString(R.string.coin_added))
        view.finishActivity()
    }
}