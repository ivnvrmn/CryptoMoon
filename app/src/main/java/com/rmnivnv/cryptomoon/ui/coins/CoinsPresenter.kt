package com.rmnivnv.cryptomoon.ui.coins

import android.content.Context
import android.util.Log
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.MainCoinsListUpdatedEvent
import com.rmnivnv.cryptomoon.model.rxbus.OnDeleteCoinsMenuItemClickedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsPresenter @Inject constructor(private val context: Context,
                                         private val view: ICoins.View,
                                         private val networkRequests: NetworkRequests,
                                         private val coinsController: CoinsController,
                                         private val db: CMDatabase,
                                         private val resProvider: ResourceProvider,
                                         private val pageController: PageController,
                                         private val multiSelector: MultiSelector,
                                         private val holdingsHandler: HoldingsHandler) : ICoins.Presenter {

    private val disposable = CompositeDisposable()
    private var coins: ArrayList<DisplayCoin> = ArrayList()
    private var holdings: ArrayList<HoldingData> = ArrayList()
    private var isRefreshing = false
    private var isFirstStart = true

    override fun onCreate(coins: ArrayList<DisplayCoin>) {
        this.coins = coins
    }

    override fun onStart() {
        subscribeToObservables()
        getAllCoinsInfo()
        if (coins.isNotEmpty()) updatePrices()
        updateHoldings()
    }

    private fun subscribeToObservables() {
        addCoinsChangesObservable()
        addHoldingsChangesObservable()
        setupRxBusEventsListeners()
        addOnPageChangedObservable()
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
            if (isFirstStart) {
                isFirstStart = false
                updatePrices()
            }
            updateHoldings()
        } else {
            coins.clear()
            view.updateRecyclerView()
        }
    }

    private fun addHoldingsChangesObservable() {
        disposable.add(db.holdingsDao().getAllHoldings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHoldingsUpdate(it) }))
    }

    private fun onHoldingsUpdate(holdings: List<HoldingData>) {
        if (holdings.isNotEmpty()) {
            this.holdings.clear()
            this.holdings.addAll(holdings)
            updateHoldings()
        } else {
            view.disableTotalHoldings()
        }
    }

    private fun updateHoldings() {
        if (holdings.isNotEmpty()) {
            setTotalHoldingValue()
            setTotalHoldingsChangePercent()
            setTotalHoldingsChangeValue()
            view.enableTotalHoldings()
        }
    }

    private fun setTotalHoldingValue() {
        view.setTotalHoldingsValue("$ ${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPrice())}")
    }

    private fun setTotalHoldingsChangePercent() {
        val totalChangePercent = holdingsHandler.getTotalChangePercent()
        view.setTotalHoldingsChangePercent("${getStringWithTwoDecimalsFromDouble(totalChangePercent)}%")
        view.setTotalHoldingsChangePercentColor(getChangeColor(totalChangePercent))
    }

    private fun setTotalHoldingsChangeValue() {
        val totalChangeValue = holdingsHandler.getTotalChangeValue()
        view.setTotalHoldingsChangeValue("$${getStringWithTwoDecimalsFromDouble(totalChangeValue)}")
        view.setTotalHoldingsChangeValueColor(getChangeColor(totalChangeValue))
        setAllTimeProfitLossString(totalChangeValue)
    }

    private fun setAllTimeProfitLossString(change: Double) {
        view.setAllTimeProfitLossString(getProfitLossText(change))
    }

    private fun getProfitLossText(change: Double) = if (change >= 0) resProvider.getString(R.string.profit) else  resProvider.getString(R.string.loss)

    private fun setupRxBusEventsListeners() {
        disposable.add(RxBus.listen(OnDeleteCoinsMenuItemClickedEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onDeleteClicked() })
    }

    private fun onDeleteClicked() {
        val coinsToDelete = coins.filter { it.selected }
        if (coinsToDelete.isNotEmpty()) {
            disableSelected()
            coinsController.deleteDisplayCoins(coinsToDelete)
            RxBus.publish(MainCoinsListUpdatedEvent())
            context.toastShort(if (coinsToDelete.size > 1) resProvider.getString(R.string.coins_deleted)
                               else resProvider.getString(R.string.coin_deleted))
        }
    }

    private fun addOnPageChangedObservable() {
        disposable.add(pageController.getPageObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onPageChanged(it) })
    }

    private fun onPageChanged(position: Int) {
        if (position != COINS_FRAGMENT_PAGE_POSITION) {
            disableSelected()
        }
    }

    private fun disableSelected() {
        if (multiSelector.atLeastOneIsSelected) {
            coins.forEach { if (it.selected) it.selected = false }
            view.updateRecyclerView()
            multiSelector.atLeastOneIsSelected = false
        }
    }

    private fun getAllCoinsInfo() {
        disposable.add(networkRequests.getAllCoins()
                .subscribe({ onAllCoinsReceived(it) },
                        { Log.e("getAllCoinsInfo", it.toString()) }))
    }

    private fun onAllCoinsReceived(list: ArrayList<InfoCoin>) {
        if (list.isNotEmpty()) coinsController.saveAllCoinsInfo(list)
    }

    override fun onViewCreated() {

    }

    private fun updatePrices() {
        val queryMap = createCoinsMapWithCurrencies(coins)
        if (queryMap.isNotEmpty()) {
            RxBus.publish(CoinsLoadingEvent(true))
            disposable.add(networkRequests.getPrice(queryMap)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onPriceUpdated(it) }, { afterRefreshing() }))
        }
    }

    private fun onPriceUpdated(list: ArrayList<DisplayCoin>) {
        if (list.isNotEmpty()) coinsController.saveDisplayCoinList(filterList(list))
        afterRefreshing()
    }

    private fun filterList(coinsInfoList: ArrayList<DisplayCoin>): ArrayList<DisplayCoin> {
        val result: ArrayList<DisplayCoin> = ArrayList()
        coins.forEach { (from, to) ->
            val find = coinsInfoList.find { it.from == from && it.to == to }
            if (find != null) result.add(find)
        }
        return result
    }

    private fun afterRefreshing() {
        RxBus.publish(CoinsLoadingEvent(false))
        if (isRefreshing) {
            view.hideRefreshing()
            isRefreshing = false
            view.enableSwipeToRefresh()
        }
    }

    override fun onStop() {
        disposable.clear()
        disableSelected()
        RxBus.publish(CoinsLoadingEvent(false))
    }

    override fun onSwipeUpdate() {
        disableSelected()
        isRefreshing = true
        updatePrices()
    }

    override fun onCoinClicked(coin: DisplayCoin) {
        view.startCoinInfoActivity(coin.from, coin.to)
    }

    override fun onHoldingsClicked() {
        view.startHoldingsActivity()
    }

    override fun onAllocationsClicked() {
        view.startAllocationsActivity()
    }
}