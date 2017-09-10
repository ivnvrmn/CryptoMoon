package com.rmnivnv.cryptomoon.di

import com.rmnivnv.cryptomoon.model.BASE_CRYPTOCOMPARE_URL
import com.rmnivnv.cryptomoon.model.network.CoinMarketCapApi
import com.rmnivnv.cryptomoon.model.network.CryptoCompareAPI
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by rmnivnv on 12/07/2017.
 */
@Module
class NetworkModule {

    @Provides @Singleton
    fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_CRYPTOCOMPARE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides @Singleton
    fun provideCrComApi(retrofit: Retrofit): CryptoCompareAPI = retrofit.create(CryptoCompareAPI::class.java)

    @Provides @Singleton
    fun provideCoinMarketCapApi(retrofit: Retrofit): CoinMarketCapApi = retrofit.create(CoinMarketCapApi::class.java)

    @Provides @Singleton
    fun provideNetworkRequests(cryptoCompareAPI: CryptoCompareAPI, coinMarketCapApi: CoinMarketCapApi) =
            NetworkRequests(cryptoCompareAPI, coinMarketCapApi)
}