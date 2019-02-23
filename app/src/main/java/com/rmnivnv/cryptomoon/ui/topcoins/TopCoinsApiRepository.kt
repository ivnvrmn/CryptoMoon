package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.network.BaseRepository
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import com.rmnivnv.cryptomoon.model.network.data.TopCoinsResponse
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import java.io.IOException

class TopCoinsApiRepository(
    private val cryptoCompareAPI: CryptoCompareAPI,
    private val resourceProvider: ResourceProvider
) : BaseRepository(), TopCoinsContract.ApiRepository {

    override suspend fun getTopCoins() = safeApiCall(
        { topCoins() },
        resourceProvider.getString(R.string.error)
    )

    private suspend fun topCoins(): Result<TopCoinsResponse> {
        val response = cryptoCompareAPI.getTopCoins().await()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(IOException(resourceProvider.getString(R.string.error)))
        } else {
            Result.Error(IOException(resourceProvider.getString(R.string.error)))
        }
    }
}