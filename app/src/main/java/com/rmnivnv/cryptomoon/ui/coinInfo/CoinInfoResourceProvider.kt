package com.rmnivnv.cryptomoon.ui.coinInfo

import android.content.Context
import android.support.v4.content.ContextCompat
import com.rmnivnv.cryptomoon.R

class CoinInfoResourceProvider(
    private val context: Context
) : CoinInfoContract.ResourceProvider {

    override fun getGreyColor() = ContextCompat.getColor(context, R.color.grey)

    override fun getGreenColor() = ContextCompat.getColor(context, R.color.green)

    override fun getOrangeDarkColor() = ContextCompat.getColor(context, R.color.orange_dark)

    override fun getRedColor() = ContextCompat.getColor(context, R.color.red)
}