package com.rmnivnv.cryptomoon.model.network

import com.rmnivnv.cryptomoon.model.TopCoinData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CoinMarketCapApi {

    @GET
    fun getTopCoins(
        @Url url: String,
        @Query("limit") limit: Int
    ): Deferred<Response<List<TopCoinData>>>
}
