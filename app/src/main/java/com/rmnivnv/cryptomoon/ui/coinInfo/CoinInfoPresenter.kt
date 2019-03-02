package com.rmnivnv.cryptomoon.ui.coinInfo

import android.graphics.Paint
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.data.HistoricalEntity
import com.rmnivnv.cryptomoon.model.data.HistoryPeriod
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.HistoricalDataResponse
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.toCryptoCompareImageUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by ivanov_r on 17.08.2017.
 */
class CoinInfoPresenter @Inject constructor(
    private val view: CoinInfoContract.View,
    private val observables: CoinInfoContract.Observables,
    private val apiRepository: CoinInfoContract.ApiRepository,
    private val resourceProvider: CoinInfoContract.ResourceProvider,
    private val logger: Logger
) : CoinInfoContract.Presenter {

    private val disposable = CompositeDisposable()
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private lateinit var coin: CoinEntity

    override fun onCreate(coin: CoinEntity) {
        this.coin = coin
        setCoinInfo()
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        disposable.add(observables.observeTransactions { setupHoldings() })
    }

    private fun setCoinInfo() = with(view) {
        coin.display.usd.apply {
            showTitle(coin.raw.usd.fromSymbol)
            showMainPrice(price)
            showLogo(imageUrl.toCryptoCompareImageUrl())
            showOpen24Hour(open24Hour)
            showHigh24Hour(high24Hour)
            showLow24Hour(low24Hour)
            showChange24Hour(change24Hour)
            showChangePct24Hour(change24Hour)
            showSupply(supply)
            showMarketCap(marketCap)
        }
        setupSpinner()
//        setupHoldings()
    }

    private fun setupHoldings() {
//        val holdingData = holdingsHandler.isThereSuchHolding(coin.from, coin.to)
//        if (holdingData != null) {
//            view.setHoldingQuantity(holdingData.quantity.toString())
//            view.setHoldingValue("$${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holdingData))}")
//
//            val changePercent = holdingsHandler.getChangePercentByHoldingData(holdingData)
//            view.setHoldingChangePercent("${getStringWithTwoDecimalsFromDouble(changePercent)}%")
//            view.setHoldingChangePercentColor(getChangeColor(changePercent))
//
//            view.setHoldingProfitLoss(getProfitLossTextBig(holdingsHandler.getChangeValueByHoldingData(holdingData), resProvider))
//            val changeValue = holdingsHandler.getChangeValueByHoldingData(holdingData)
//            view.setHoldingProfitValue("$${getStringWithTwoDecimalsFromDouble(changeValue)}")
//            view.setHoldingProfitValueColor(getChangeColor(changeValue))
//
//            view.setHoldingTradePrice("$${holdingData.price}")
//            view.setHoldingTradeDate(formatLongDateToString(holdingData.date, DEFAULT_DATE_FORMAT))
//            view.enableHoldings()
//        } else {
//            view.disableHoldings()
//        }
    }

    override fun onSpinnerItemClicked(position: Int) {
        view.enableGraphLoading()
        requestHistory(position)
    }

    private fun requestHistory(position: Int) {
        val period = when (position) {
            0 -> HistoryPeriod.HOUR
            1 -> HistoryPeriod.HOURS_12
            2 -> HistoryPeriod.HOURS_24
            3 -> HistoryPeriod.DAYS_3
            4 -> HistoryPeriod.WEEK
            5 -> HistoryPeriod.MONTH
            6 -> HistoryPeriod.MONTHS_3
            7 -> HistoryPeriod.MONTHS_6
            8 -> HistoryPeriod.YEAR
            else -> HistoryPeriod.MONTH
        }

        scope.launch {
            val result = apiRepository.requestHistory(period, coin.raw.usd.fromSymbol)
            when (result) {
                is Result.Success -> { onHistoryReceived(result.data, period) }
                is Result.Error -> { logger.logError("requestHistory ${result.exception}") }
            }
            scope.launch(Dispatchers.Main) { view.disableGraphLoading() }
        }
    }

    private fun onHistoryReceived(response: HistoricalDataResponse, period: HistoryPeriod) {
        val data = response.data
        if (data.isNullOrEmpty()) {
            view.enableEmptyGraphText()
            return
        }

        view.disableEmptyGraphText()
        view.drawChart(createCandleData(data, period))
    }

    private fun createCandleData(data: List<HistoricalEntity>, period: HistoryPeriod): CandleData {
        val candleList: ArrayList<CandleEntry> = arrayListOf()
        var x = 0f
        data.forEach {
            candleList.add(CandleEntry(++x, it.high, it.low, it.open, it.close))
        }
        val dataSet = CandleDataSet(candleList.toList(), period.title).apply {
            shadowColor = resourceProvider.getGreyColor()
            shadowWidth = 0.7f
            decreasingColor = resourceProvider.getRedColor()
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = resourceProvider.getGreenColor()
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = resourceProvider.getOrangeDarkColor()
            setDrawValues(false)
        }
        return CandleData(dataSet)
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onAddTransactionClicked() {
//        view.startAddTransactionActivity(coin.from, coin.to, coin.price)
    }
}