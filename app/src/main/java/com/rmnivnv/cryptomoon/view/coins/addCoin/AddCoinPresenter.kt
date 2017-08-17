package com.rmnivnv.cryptomoon.view.coins.addCoin

import android.util.Log
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
    @Inject lateinit var db: CMDatabase

    private val disposable = CompositeDisposable()
    private var allCoins: List<InfoCoin> = mutableListOf()
    private var coins: ArrayList<DisplayCoin> = ArrayList()
    private lateinit var matches: ArrayList<InfoCoin>

    override fun onCreate(component: AddCoinComponent, matches: ArrayList<InfoCoin>) {
        component.inject(this)
        this.matches = matches
        addAllInfoCoinsChangesObservable()
        addCoinsChangesObservable()
    }

    private fun addAllInfoCoinsChangesObservable() {
        disposable.add(db.allCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .subscribe({ onAllCoinsUpdates(it) }))
    }

    private fun onAllCoinsUpdates(coinsList: List<InfoCoin>) {
        Log.d("All coins size ", coinsList.size.toString())
        if (coinsList.isNotEmpty()) {
            allCoins = coinsList
        }
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
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onFromTextChanged(text: String) {
        view.enableMatchesCount()
        if (text.isNotEmpty() && allCoins.isNotEmpty()) {
            val matchesList = allCoins.filter { (it.coinName.contains(text, true)) ||
                    (it.name.contains(text, true)) }.reversed()
            if (matchesList.isNotEmpty()) {
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
        if (coins.isNotEmpty() && coins.find { it.from == coin.name } != null) {
            app.toastShort(resProvider.getString(R.string.coin_already_added))
        } else {
            view.disableMatchesCount()
            matches.clear()
            matches.add(coin)
            view.updateRecyclerView()
            view.hideKeyboard()
            requestCoinInfo(coin)
        }
    }

    private fun requestCoinInfo(coin: InfoCoin) {
        if (!coin.name.isEmpty()) {
            view.enableLoadingLayout()
            disposable.add(networkRequests.getPrice(createCoinsMapFromString(coin.name), object : GetPriceCallback {
                override fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?) {
                    if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                        coinsController.saveDisplayCoinList(coinsInfoList)
                        coinSuccessfullyAdded()
                    } else {
                        view.disableLoadingLayout()
                        app.toastShort(resProvider.getString(R.string.coin_not_found))
                    }
                }

                override fun onError(t: Throwable) {
                    view.disableLoadingLayout()
                    app.toastShort(resProvider.getString(R.string.coin_not_found))
                    Log.d("onError", t.message)
                }
            }))
        }
    }

    private fun coinSuccessfullyAdded() {
        app.toastShort(resProvider.getString(R.string.coin_added))
        view.finishActivity()
    }
}