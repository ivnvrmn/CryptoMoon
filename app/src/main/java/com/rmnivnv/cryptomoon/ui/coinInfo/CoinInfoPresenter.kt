package com.rmnivnv.cryptomoon.ui.coinInfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 17.08.2017.
 */
class CoinInfoPresenter @Inject constructor(private val context: Context,
                                            private val view: ICoinInfo.View,
                                            private val coinsController: CoinsController,
                                            private val networkRequests: NetworkRequests,
                                            private val graphMaker: GraphMaker,
                                            private val holdingsHandler: HoldingsHandler,
                                            private val resProvider: ResourceProvider) : ICoinInfo.Presenter {

    private val disposable = CompositeDisposable()
    private var coin: DisplayCoin? = null
    private var from: String? = null
    private var to: String? = null

    override fun onCreate(extras: Bundle) {
        if (extras[NAME] != null && extras[TO] != null) {
            from = extras.getString(NAME)
            to = extras.getString(TO)
            getCoinByName(from, to)
        }
    }

    private fun getCoinByName(from: String?, to: String?) {
        disposable.add(Single.fromCallable { coinsController.getDisplayCoin(from!!, to!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinArrived(it) }, { onFindCoinError(it) }))
    }

    private fun onCoinArrived(coin: DisplayCoin) {
        this.coin = coin
        view.setTitle(coin.fullName)
        view.setLogo(coin.imgUrl)
        view.setMainPrice(coin.PRICE)
        requestHisto(MONTH)
        setCoinInfo()
        setupHoldings()
    }

    private fun requestHisto(period: String) {
        disposable.add(networkRequests.getHistoPeriod(period, coin?.from, coin?.to)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHistoReceived(it, period) }, { view.disableGraphLoading() }))
    }

    private fun onHistoReceived(histoList: ArrayList<HistoData>, period: String) {
        view.disableGraphLoading()
        view.drawChart(graphMaker.makeChart(histoList, period))
    }

    private fun setCoinInfo() {
        view.setOpen(coin!!.OPEN24HOUR)
        view.setHigh(coin!!.HIGH24HOUR)
        view.setLow(coin!!.LOW24HOUR)
        view.setChange(coin!!.CHANGE24HOUR)
        view.setChangePct(coin!!.CHANGEPCT24HOUR)
        view.setSupply(coin!!.SUPPLY)
        view.setMarketCap(coin!!.MKTCAP)
    }

    private fun setupHoldings() {
        val holdingData = holdingsHandler.isThereSuchHolding(coin?.from, coin?.to)
        if (holdingData != null) {
            view.setHoldingQuantity(holdingData.quantity.toString())
            view.setHoldingValue("$${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holdingData))}")

            val changePercent = holdingsHandler.getChangePercentByHoldingData(holdingData)
            view.setHoldingChangePercent("${getStringWithTwoDecimalsFromDouble(changePercent)}%")
            view.setHoldingChangePercentColor(getChangeColor(changePercent))

            view.setHoldingProfitLoss(getProfitLossTextBig(holdingsHandler.getChangeValueByHoldingData(holdingData), resProvider))
            val changeValue = holdingsHandler.getChangeValueByHoldingData(holdingData)
            view.setHoldingProfitValue("$${getStringWithTwoDecimalsFromDouble(changeValue)}")
            view.setHoldingProfitValueColor(getChangeColor(changeValue))

            view.setHoldingTradePrice("$${holdingData.price}")
            view.setHoldingTradeDate(formatLongDateToString(holdingData.date, DEFAULT_DATE_FORMAT))
            view.enableHoldings()
        } else {
            view.disableHoldings()
        }
    }

    private fun onFindCoinError(throwable: Throwable) {
        context.logDebug("getCoinByName error " + throwable.toString())
        requestCoinInfo(DisplayCoin(from = this.from!!, to = this.to!!))
    }

    private fun requestCoinInfo(coin: DisplayCoin) {
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coin)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onPriceUpdated(it) }, { Log.e("requestCoinInfo", it.toString()) }))
    }

    private fun onPriceUpdated(list: ArrayList<DisplayCoin>) {
        if (list.isNotEmpty()) {
            val arrivedCoin = list[0]
            coinsController.addAdditionalInfo(arrivedCoin)
            onCoinArrived(arrivedCoin)
        }
    }

    override fun onSpinnerItemClicked(selectedItem: String) {
        if (coin != null) {
            view.enableGraphLoading()
            requestHisto(selectedItem)
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onAddTransactionClicked() {
        view.startAddTransactionActivity(coin?.from, coin?.to, coin?.PRICE)
    }
}