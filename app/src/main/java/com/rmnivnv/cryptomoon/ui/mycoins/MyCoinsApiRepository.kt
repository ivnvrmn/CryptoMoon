package com.rmnivnv.cryptomoon.ui.mycoins

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.network.BaseRepository
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import com.rmnivnv.cryptomoon.model.network.data.PricesResponse
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import java.io.IOException

class MyCoinsApiRepository(
    private val cryptoCompareAPI: CryptoCompareAPI,
    private val resourceProvider: ResourceProvider
) : BaseRepository(), MyCoinsContract.ApiRepository {

    override suspend fun getPrices(coins: String) = safeApiCall(
        { prices(coins) },
        resourceProvider.getString(R.string.error)
    )

    private suspend fun prices(coins: String): Result<PricesResponse> {
        val response = cryptoCompareAPI.getPricesAsync(coins).await()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(IOException(resourceProvider.getString(R.string.error)))
        } else {
            Result.Error(IOException(resourceProvider.getString(R.string.error)))
        }
    }
}