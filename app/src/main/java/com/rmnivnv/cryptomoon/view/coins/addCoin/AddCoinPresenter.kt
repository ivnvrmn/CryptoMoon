package com.rmnivnv.cryptomoon.view.coins.addCoin

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.DBController
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.toastShort
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by rmnivnv on 27/07/2017.
 */
class AddCoinPresenter : IAddCoin.Presenter {

    @Inject lateinit var view: IAddCoin.View
    @Inject lateinit var dbController: DBController
    @Inject lateinit var networkRequests: NetworkRequests
    @Inject lateinit var prefProvider: PreferencesProvider
    @Inject lateinit var app: MainApp
    @Inject lateinit var resProvider: ResourceProvider

    private val disposable = CompositeDisposable()
    private var allCoins: List<Coin>? = null
    private lateinit var matches: ArrayList<Coin>
    private var fromCoin: Coin = Coin()

    override fun onCreate(component: AddCoinComponent, matches: ArrayList<Coin>) {
        component.inject(this)
        this.matches = matches
        checkAllCoins()
    }

    private fun checkAllCoins() {
        dbController.getAllCoins(object : GetAllCoinsFromDbCallback {
            override fun onSuccess(list: List<Coin>) {
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
            val matchesList = allCoins?.filter {
                (it.coinName.contains(text, true)) || (it.name.contains(text, true))
            }?.reversed()
            if (matchesList != null && matchesList.isNotEmpty()) {
                view.setMatchesResultSize(matchesList.size.toString())
                matches.clear()
                matches.addAll(matchesList)
                view.updateRecyclerView()
            } else {
                view.setMatchesResultSize("0")
            }
        } else {
            matches.clear()
            view.updateRecyclerView()
            view.setMatchesResultSize("0")
        }
    }

    override fun onToTextChanged(text: String) {

    }

    override fun onFromItemClicked(coin: Coin) {
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
            override fun onSuccess(coinsInfoList: ArrayList<CoinBodyDisplay>?) {
                view.disableLoadingLayout()
                if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                    coinsInfoList.forEach {
                        println(it.toString())
                        saveCoinToPreferences(it)
                    }
                } else {
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

    private fun saveCoinToPreferences(coin: CoinBodyDisplay) {
        val map = prefProvider.getSelectedCoins()
        val fsymsArray = map[FSYMS]
        fsymsArray!!.forEach {
            if (it == coin.FROMSYMBOL) {
                app.toastShort(resProvider.getString(R.string.coin_already_added))
                return
            }
        }
        fsymsArray.add(coin.FROMSYMBOL)
        map.put(FSYMS, fsymsArray)
        prefProvider.setSelectedCoins(map)
        app.toastShort(resProvider.getString(R.string.coin_added))
        view.finishActivity()
    }
}