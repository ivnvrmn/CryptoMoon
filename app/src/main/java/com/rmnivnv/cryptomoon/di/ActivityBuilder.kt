package com.rmnivnv.cryptomoon.di

import com.rmnivnv.cryptomoon.ui.addCoin.AddCoinActivity
import com.rmnivnv.cryptomoon.ui.addCoin.AddCoinModule
import com.rmnivnv.cryptomoon.ui.addTransaction.AddTransactionActivity
import com.rmnivnv.cryptomoon.ui.addTransaction.AddTransactionModule
import com.rmnivnv.cryptomoon.ui.coinAllocation.CoinAllocationActivity
import com.rmnivnv.cryptomoon.ui.coinAllocation.CoinAllocationModule
import com.rmnivnv.cryptomoon.ui.coinInfo.CoinInfoActivity
import com.rmnivnv.cryptomoon.ui.coinInfo.CoinInfoModule
import com.rmnivnv.cryptomoon.ui.holdings.HoldingsActivity
import com.rmnivnv.cryptomoon.ui.holdings.HoldingsModule
import com.rmnivnv.cryptomoon.ui.main.MainActivity
import com.rmnivnv.cryptomoon.ui.main.MainFragmentProvider
import com.rmnivnv.cryptomoon.ui.main.MainModule
import com.rmnivnv.cryptomoon.ui.settings.SettingsActivity
import com.rmnivnv.cryptomoon.ui.settings.SettingsModule
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

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(CoinAllocationModule::class))
    abstract fun bindCoinAllocationActivity(): CoinAllocationActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(AddTransactionModule::class))
    abstract fun bindAddTransactionActivity(): AddTransactionActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(HoldingsModule::class))
    abstract fun bindHoldingsActivity(): HoldingsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun bindSettingsActivity(): SettingsActivity
}