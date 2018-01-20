package com.rmnivnv.cryptomoon.ui.coinInfo

import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.rxbus.TransactionAdded
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 17.08.2017.
 */
class CoinInfoPresenter @Inject constructor(private val view: ICoinInfo.View,
                                            private val coinsController: CoinsController,
                                            private val networkRequests: NetworkRequests,
                                            private val graphMaker: GraphMaker,
                                            private val holdingsHandler: HoldingsHandler,
                                            private val resProvider: ResourceProvider,
                                            private val logger: Logger) : ICoinInfo.Presenter {

    private val disposable = CompositeDisposable()
    private var coin: Coin = Coin(from = "", to = "")
    private lateinit var from: String
    private lateinit var to: String

    override fun onCreate(fromArg: String, toArg: String) {
        from = fromArg
        to = toArg
        setObservables()
        getCoinByName(from, to)
    }

    private fun setObservables() {
        disposable.add(RxBus.listen(TransactionAdded::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { setupHoldings() })
    }

    private fun getCoinByName(from: String?, to: String?) {
        disposable.add(Single.fromCallable { coinsController.getCoin(from!!, to!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinArrived(it) }, { onFindCoinError(it) }))
    }

    private fun onCoinArrived(coin: Coin) {
        this.coin = coin
        view.setTitle(coin.fullName)
        view.setLogo(coin.imgUrl)
        view.setMainPrice(coin.price)
        setCoinInfo()
        setupHoldings()
        view.setupSpinner()
    }

    private fun setCoinInfo() {
        view.setOpen(coin.open24h)
        view.setHigh(coin.high24h)
        view.setLow(coin.low24h)
        view.setChange(coin.change24h)
        view.setChangePct(coin.changePct24h)
        view.setSupply(coin.supply)
        view.setMarketCap(coin.mktCap)
    }

    private fun setupHoldings() {
        val holdingData = holdingsHandler.isThereSuchHolding(coin.from, coin.to)
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
        logger.logDebug("getCoinByName error " + throwable.toString())
        requestCoinInfo(Coin(from = this.from, to = this.to))
    }

    private fun requestCoinInfo(coin: Coin) {
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coin)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onPriceUpdated(it) }, { logger.logError("requestCoinInfo $it") }))
    }

    private fun onPriceUpdated(list: ArrayList<Coin>) {
        if (list.isNotEmpty()) {
            val arrivedCoin = list[0]
            coinsController.addAdditionalInfoToCoin(arrivedCoin)
            onCoinArrived(arrivedCoin)
        }
    }

    override fun onSpinnerItemClicked(position: Int) {
        view.enableGraphLoading()
        requestHisto(when (position) {
            0 -> HOUR
            1 -> HOURS12
            2 -> HOURS24
            3 -> DAYS3
            4 -> WEEK
            5 -> MONTH
            6 -> MONTHS3
            7 -> MONTHS6
            8 -> YEAR
            else -> MONTH
        })
    }

    private fun requestHisto(period: String) {
        disposable.add(networkRequests.getHistoPeriod(period, coin.from, coin.to)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHistoReceived(it, period) }, { view.disableGraphLoading() }))
    }

    private fun onHistoReceived(histoList: ArrayList<HistoData>, period: String) {
        view.disableGraphLoading()
        if (histoList.isNotEmpty()) {
            view.drawChart(graphMaker.makeChart(histoList, period))
            view.disableEmptyGraphText()
        } else {
            view.enableEmptyGraphText()
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onAddTransactionClicked() {
        view.startAddTransactionActivity(coin.from, coin.to, coin.price)
    }
}