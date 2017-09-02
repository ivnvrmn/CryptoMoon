package com.rmnivnv.cryptomoon.di

import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.view.main.MainComponent
import com.rmnivnv.cryptomoon.view.main.MainModule
import com.rmnivnv.cryptomoon.view.coins.CoinsComponent
import com.rmnivnv.cryptomoon.view.coins.CoinsModule
import com.rmnivnv.cryptomoon.view.coins.addCoin.AddCoinComponent
import com.rmnivnv.cryptomoon.view.coins.addCoin.AddCoinModule
import com.rmnivnv.cryptomoon.view.coins.coinInfo.CoinInfoComponent
import com.rmnivnv.cryptomoon.view.coins.coinInfo.CoinInfoModule
import com.rmnivnv.cryptomoon.view.topCoins.TopCoinsComponent
import com.rmnivnv.cryptomoon.view.topCoins.TopCoinsModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by rmnivnv on 05/07/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {
    fun inject(app: MainApp)

    fun plus(mainModule: MainModule): MainComponent
    fun plus(coinsModule: CoinsModule): CoinsComponent
    fun plus(addCoinModule: AddCoinModule): AddCoinComponent
    fun plus(coinInfoModule: CoinInfoModule): CoinInfoComponent
    fun plus(topCoinsModule: TopCoinsModule): TopCoinsComponent
}