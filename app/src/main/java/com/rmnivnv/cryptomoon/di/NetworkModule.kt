package com.rmnivnv.cryptomoon.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by rmnivnv on 12/07/2017.
 */

const val CRYPTO_COMPARE_IMAGE_URL = "https://www.cryptocompare.com/"
private const val BASE_CRYPTO_COMPARE_URL = "https://min-api.cryptocompare.com/data/"

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_CRYPTO_COMPARE_URL)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @Singleton
    fun provideCryptoCompareApi(retrofit: Retrofit): CryptoCompareAPI {
        return retrofit.create(CryptoCompareAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkRequests(api: CryptoCompareAPI) = NetworkRequests(api)
}