package com.rmnivnv.cryptomoon.ui.coinInfo

import com.github.mikephil.charting.data.CandleData
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.data.HistoryPeriod
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.HistoricalDataResponse
import io.reactivex.disposables.Disposable

/**
 * Created by ivanov_r on 17.08.2017.
 */
interface CoinInfoContract {

    interface View {
        fun showTitle(title: String)
        fun showLogo(url: String)
        fun showMainPrice(price: String)
        fun drawChart(line: CandleData)
        fun showOpen24Hour(open: String)
        fun showHigh24Hour(high: String)
        fun showLow24Hour(low: String)
        fun showChange24Hour(change: String)
        fun showChangePct24Hour(pct: String)
        fun showSupply(supply: String)
        fun showMarketCap(cap: String)
        fun enableGraphLoading()
        fun disableGraphLoading()
        fun startAddTransactionActivity(from: String?, to: String?, price: String?)
        fun setHoldingQuantity(quantity: String)
        fun setHoldingValue(value: String)
        fun setHoldingChangePercent(pct: String)
        fun setHoldingChangePercentColor(color: Int)
        fun setHoldingProfitLoss(profitLoss: String)
        fun setHoldingProfitValue(value: String)
        fun setHoldingProfitValueColor(color: Int)
        fun setHoldingTradePrice(price: String)
        fun setHoldingTradeDate(date: String)
        fun enableHoldings()
        fun disableHoldings()
        fun enableEmptyGraphText()
        fun disableEmptyGraphText()
        fun setupSpinner()
    }

    interface Presenter {
        fun onCreate(coin: CoinEntity)
        fun onSpinnerItemClicked(position: Int)
        fun onAddTransactionClicked()
        fun onDestroy()
    }

    interface Observables {
        fun observeTransactions(callback: () -> Unit): Disposable
    }

    interface ApiRepository {
        suspend fun requestHistory(
            period: HistoryPeriod,
            coinFrom: String
        ): Result<HistoricalDataResponse>
    }

    interface ResourceProvider {
        fun getGreyColor(): Int
        fun getRedColor(): Int
        fun getGreenColor(): Int
        fun getOrangeDarkColor(): Int
    }
}