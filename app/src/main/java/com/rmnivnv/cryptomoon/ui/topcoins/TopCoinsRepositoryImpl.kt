package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.COIN_MARKET_CAP_URL_TICKER
import com.rmnivnv.cryptomoon.model.TopCoinData
import com.rmnivnv.cryptomoon.model.network.BaseRepository
import com.rmnivnv.cryptomoon.model.network.CoinMarketCapApi

class TopCoinsRepositoryImpl(
    private val api: CoinMarketCapApi
) : BaseRepository(), TopCoinsRepository {

    override suspend fun getTopCoins(): List<TopCoinData>? {
        return safeApiCall(
            { api.getTopCoins(COIN_MARKET_CAP_URL_TICKER, 100).await() },
            "errorMessage"
        )
    }
}