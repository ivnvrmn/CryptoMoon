package com.rmnivnv.cryptomoon.model.network

import com.rmnivnv.cryptomoon.model.TopCoinData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by rmnivnv on 31/08/2017.
 */
interface CoinMarketCapApi {

    @GET
    fun getTopCoins(@Url url: String,  @Query("limit") limit: Int): Single<List<TopCoinData>>
}