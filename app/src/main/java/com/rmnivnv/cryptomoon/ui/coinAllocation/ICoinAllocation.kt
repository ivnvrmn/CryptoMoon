package com.rmnivnv.cryptomoon.ui.coinAllocation

import com.github.mikephil.charting.data.PieData
import com.rmnivnv.cryptomoon.model.DisplayCoin


interface ICoinAllocation {

    interface View {
        fun closeActivity()
        fun drawPieChart(pieData: PieData)
        fun enableGraphLoading()
        fun disableGraphLoading()
    }

    interface Presenter {
        fun onCreate(coins: List<DisplayCoin>)
        fun onDestroy()
    }
}