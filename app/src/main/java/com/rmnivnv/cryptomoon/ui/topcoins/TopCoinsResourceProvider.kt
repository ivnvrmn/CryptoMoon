package com.rmnivnv.cryptomoon.ui.topcoins

import android.content.Context
import com.rmnivnv.cryptomoon.R

class TopCoinsResourceProvider(
    private val context: Context
) : TopCoinsContract.ResourceProvider {
    override fun getCoinAddedText(): String = context.getString(R.string.coin_added)
}