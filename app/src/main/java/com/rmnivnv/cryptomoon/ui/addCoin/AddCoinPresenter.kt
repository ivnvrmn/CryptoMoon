package com.rmnivnv.cryptomoon.ui.addCoin

import android.content.Context
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 27/07/2017.
 */
class AddCoinPresenter @Inject constructor(private val context: Context,
                                           private val view: IAddCoin.View,
                                           private val coinsController: CoinsController,
                                           private val networkRequests: NetworkRequests,
                                           private val resProvider: ResourceProvider,
                                           private val db: CMDatabase): IAddCoin.Presenter {

    private val disposable = CompositeDisposable()
    private var allCoins: List<InfoCoin> = mutableListOf()
    private var coins: ArrayList<Coin> = ArrayList()
    private lateinit var matches: ArrayList<InfoCoin>

    override fun onCreate( matches: ArrayList<InfoCoin>) {
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
        if (coinsList.isNotEmpty()) {
            allCoins = coinsList
        }
    }

    private fun addCoinsChangesObservable() {
        disposable.add(db.coinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsFromDbUpdates(it) }))
    }

    private fun onCoinsFromDbUpdates(list: List<Coin>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
        }
    }

    override fun onStop() {
        disposable.clear()
    }

    override fun observeFromText(observable: Observable<CharSequence>) {
        disposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFromTextArrived))
    }

    private fun onFromTextArrived(char: CharSequence) {
        val text = char.toString()
        view.enableMatchesCount()
        if (text.isNotEmpty() && allCoins.isNotEmpty()) {
            val matchesList = allCoins.filter { (it.coinName.contains(text, true)) ||
                    (it.name.contains(text, true)) }.reversed()
            if (matchesList.isNotEmpty()) {
                matches.clear()
                matches.addAll(checkAndRemoveAlreadyAddedCoins(matchesList))
                updateCoinsList()
            } else {
                updateCoinsList()
            }
        } else {
            updateCoinsList()
        }
    }

    private fun updateCoinsList() {
        view.setMatchesResultSize(if (matches.size > 0) matches.size.toString() else "0")
        view.updateRecyclerView()
    }

    private fun checkAndRemoveAlreadyAddedCoins(list: List<InfoCoin>): List<InfoCoin> {
        val result: ArrayList<InfoCoin> = arrayListOf()
        result.addAll(list)
        list.forEach {
            if (coinsController.coinAlreadyAdded(it.name)) {
                result.remove(it)
            }
        }
        return result
    }

    override fun onFromItemClicked(coin: InfoCoin) {
        view.disableMatchesCount()
        matches.clear()
        matches.add(coin)
        view.updateRecyclerView()
        view.hideKeyboard()
        requestCoinInfo(Coin(from = coin.name, to = USD))
    }

    private fun requestCoinInfo(coin: Coin) {
        view.enableLoadingLayout()
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coin)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onPriceUpdated(it) }, { coinNotFound() }))
    }

    private fun onPriceUpdated(list: ArrayList<Coin>) {
        if (list.isNotEmpty()) {
            coinsController.saveCoinsList(list)
            coinSuccessfullyAdded()
        } else {
            coinNotFound()
        }
    }

    private fun coinNotFound() {
        view.disableLoadingLayout()
        context.toastShort(resProvider.getString(R.string.coin_not_found))
    }

    private fun coinSuccessfullyAdded() {
        context.toastShort(resProvider.getString(R.string.coin_added))
        view.finishActivity()
    }
}