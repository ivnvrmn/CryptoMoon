package com.rmnivnv.cryptomoon.view.coins.coinInfo

import android.os.Bundle
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
    }

    interface Presenter {
        fun onCreate(component: CoinInfoComponent, extras: Bundle)
        fun onDestroy()
        fun onSpinnerItemClicked(selectedItem: String)
    }
}