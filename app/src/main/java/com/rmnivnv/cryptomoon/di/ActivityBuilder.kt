package com.rmnivnv.cryptomoon.di

import com.rmnivnv.cryptomoon.ui.coins.addCoin.AddCoinActivity
import com.rmnivnv.cryptomoon.ui.coins.addCoin.AddCoinModule
import com.rmnivnv.cryptomoon.ui.coins.coinInfo.CoinInfoActivity
import com.rmnivnv.cryptomoon.ui.coins.coinInfo.CoinInfoModule
import com.rmnivnv.cryptomoon.ui.main.MainActivity
import com.rmnivnv.cryptomoon.ui.main.MainFragmentProvider
import com.rmnivnv.cryptomoon.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by ivanov_r on 05.09.2017.
 */
@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class, MainFragmentProvider::class))
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(AddCoinModule::class))
    abstract fun bindAddCoinActivity(): AddCoinActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(CoinInfoModule::class))
    abstract fun bindCoinInfoActivity(): CoinInfoActivity
}