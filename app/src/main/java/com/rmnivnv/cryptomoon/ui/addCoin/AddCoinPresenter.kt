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
    private var coins: ArrayList<DisplayCoin> = ArrayList()
    private lateinit var matches: ArrayList<InfoCoin>
    private var selectedCoin: DisplayCoin? = null
    private var coinSelect = true

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
        matches.clear()
        matches.add(coin)
        view.updateRecyclerView()
        view.hideKeyboard()
        if (coinSelect) {
            selectedCoin = DisplayCoin(from = coin.name, to = "")
            requestPairs(coin)
            coinSelect = false
        } else {
            if (selectedCoin != null) {
                selectedCoin?.to = coin.coinName.replace("/ ", "")
                if (coins.isNotEmpty() && coins.find { it.from == selectedCoin?.from &&
                        it.to == selectedCoin?.to} != null) {
                    context.toastShort(resProvider.getString(R.string.coin_already_added))
                    matches.clear()
                    view.updateRecyclerView()
                    coinSelect = true
                    view.clearFromEdt()
                } else {
                    requestCoinInfo(selectedCoin!!)
                }
            }
        }
    }

    private fun requestPairs(coin: InfoCoin) {
        if (!coin.name.isEmpty()) {
            view.enableLoadingLayout()
            disposable.add(networkRequests.getPairs(coin.name)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onPairsArrived(it) }, { view.disableLoadingLayout() }))
        }
    }

    private fun onPairsArrived(pairs: ArrayList<PairData>) {
        view.disableLoadingLayout()
        onPairsReceived(pairs)
    }

    private fun onPairsReceived(pairs: ArrayList<PairData>) {
        if (pairs.isNotEmpty()) {
            matches.clear()
            pairs.forEach {
                matches.add(InfoCoin(name = it.fromSymbol, coinName = """/ ${it.toSymbol}""", imageUrl = "", coinId = ""))
            }
            view.updateRecyclerView()
            context.toastShort(resProvider.getString(R.string.choose_currency))
        }
    }

    private fun requestCoinInfo(coin: DisplayCoin) {
        view.enableLoadingLayout()
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coin)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onPriceUpdated(it) }, { coinNotFound() }))
    }

    private fun onPriceUpdated(list: ArrayList<DisplayCoin>) {
        if (list.isNotEmpty()) {
            coinsController.saveDisplayCoinList(list)
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