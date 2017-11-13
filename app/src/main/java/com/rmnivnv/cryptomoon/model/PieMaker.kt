package com.rmnivnv.cryptomoon.model

import com.github.mikephil.charting.data.*
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.github.mikephil.charting.utils.ColorTemplate.*
import com.rmnivnv.cryptomoon.R

private val TEXT_SIZE_DP: Float = 17f

class PieMaker(val resProvider: ResourceProvider,
               val holdingsHandler: HoldingsHandler) {


    fun makeChart(coinList: ArrayList<DisplayCoin>): PieData {
        val pieEntryList: ArrayList<PieEntry> = ArrayList()
        coinList.forEach {

            val holding = holdingsHandler.isThereSuchHolding(it.from, it.to)
            if (holding != null) {
                val value = holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holding)
                pieEntryList.add(PieEntry(value.toFloat(), it.from))
            } else {
                println("No holding for $it")
            }
        }
        val dataSet = PieDataSet(pieEntryList.toList(), resProvider.getString(R.string.coins))
        setupDataSetParams(dataSet)

        val pieData = PieData(dataSet)
        setupChartParams(pieData)
        return pieData
    }

    private fun setupChartParams(pieData: PieData) {
        with (pieData) {
            pieData.
            setValueTextSize(TEXT_SIZE_DP)
        }
    }

    private fun setupDataSetParams(dataSet: PieDataSet) {
        with(dataSet) {
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            colors = MATERIAL_COLORS.asList()
        }
    }
}