package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.AllCoinsData
import com.rmnivnv.cryptomoon.model.TopCoinData
import com.rmnivnv.cryptomoon.model.network.Result

interface TopCoinsRepository {
    suspend fun getTopCoins(): Result<List<TopCoinData>>
    suspend fun getCoinList(): Result<AllCoinsData>
}