package com.rmnivnv.cryptomoon.model.network.api

import com.google.gson.JsonObject
import com.rmnivnv.cryptomoon.model.AllCoinsData
import com.rmnivnv.cryptomoon.model.network.data.HistoricalDataResponse
import com.rmnivnv.cryptomoon.model.network.data.PricesResponse
import com.rmnivnv.cryptomoon.model.network.data.TopCoinsResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface CryptoCompareAPI {

    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("limit") limit: Int = 100,
        @Query("tsym") tsym: String = "USD"
    ): Deferred<Response<TopCoinsResponse>>

    @GET
    fun getCoinsList(@Url url: String): Single<AllCoinsData>

    @GET
    fun getCoinListAsync(@Url url: String): Deferred<Response<AllCoinsData>>

    @GET("pricemultifull")
    fun getPrice(@Query("fsyms") from: String, @Query("tsyms") to: String): Single<JsonObject>

    @GET("pricemultifull")
    fun getPricesAsync(
        @Query("fsyms") from: String,
        @Query("tsyms") to: String = "USD"
    ): Deferred<Response<PricesResponse>>

    @GET("{period}")
    fun getHistoricalData(
        @Path("period") period: String,
        @Query("fsym") from: String,
        @Query("tsym") to: String,
        @Query("limit") limit: Int,
        @Query("aggregate") aggregate: Int
    ): Deferred<Response<HistoricalDataResponse>>

    @GET("top/pairs")
    fun getPairs(@Query("fsym") from: String): Single<JsonObject>
}