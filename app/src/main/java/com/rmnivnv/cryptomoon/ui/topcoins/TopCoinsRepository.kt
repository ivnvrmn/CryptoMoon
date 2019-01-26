package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.TopCoinData

interface TopCoinsRepository {

    suspend fun getTopCoins(): List<TopCoinData>?
}