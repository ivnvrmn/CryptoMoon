package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.AllCoinsData
import com.rmnivnv.cryptomoon.model.COINS_LIST_URL
import com.rmnivnv.cryptomoon.model.TopCoinData
import com.rmnivnv.cryptomoon.model.network.BaseRepository
import com.rmnivnv.cryptomoon.model.network.CoinMarketCapApi
import com.rmnivnv.cryptomoon.model.network.CryptoCompareAPI
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import java.io.IOException

private const val COIN_MARKET_CAP_URL_TICKER = "https://api.coinmarketcap.com/v1/ticker"
private const val LIMIT = 100

class TopCoinsRepositoryImpl(
    private val coinMarketCapApi: CoinMarketCapApi,
    private val cryptoCompareAPI: CryptoCompareAPI,
    private val resourceProvider: ResourceProvider
) : BaseRepository(), TopCoinsRepository {

    override suspend fun getTopCoins() = safeApiCall(
        { topCoins() },
        resourceProvider.getString(R.string.error)
    )

    private suspend fun topCoins(): Result<List<TopCoinData>> {
        val response = coinMarketCapApi.getTopCoins(COIN_MARKET_CAP_URL_TICKER, LIMIT).await()
        return if (response.isSuccessful) {
            Result.Success(requireNotNull(response.body()))
        } else {
            Result.Error(IOException(resourceProvider.getString(R.string.error)))
        }
    }

    override suspend fun getCoinList() = safeApiCall(
        { coinList() },
        resourceProvider.getString(R.string.error)
    )

    private suspend fun coinList(): Result<AllCoinsData> {
        val response = cryptoCompareAPI.getCoinListAsync(COINS_LIST_URL).await()
        return if (response.isSuccessful) {
            Result.Success(requireNotNull(response.body()))
        } else {
            Result.Error(IOException(resourceProvider.getString(R.string.error)))
        }
    }
}