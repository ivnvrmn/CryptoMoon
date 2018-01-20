package com.rmnivnv.cryptomoon.ui.coinInfo

import com.github.mikephil.charting.data.CandleData

/**
 * Created by ivanov_r on 17.08.2017.
 */
interface ICoinInfo {

    interface View {
        fun setTitle(title: String)
        fun setLogo(url: String)
        fun setMainPrice(price: String)
        fun drawChart(line: CandleData)
        fun setOpen(open: String)
        fun setHigh(high: String)
        fun setLow(low: String)
        fun setChange(change: String)
        fun setChangePct(pct: String)
        fun setSupply(supply: String)
        fun setMarketCap(cap: String)
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
        fun onCreate(fromArg: String, toArg: String)
        fun onSpinnerItemClicked(position: Int)
        fun onAddTransactionClicked()
        fun onDestroy()
    }
}