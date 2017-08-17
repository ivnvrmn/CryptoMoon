package com.rmnivnv.cryptomoon.view.coins.coinInfo

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by ivanov_r on 17.08.2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(CoinInfoModule::class))
interface CoinInfoComponent {
    fun inject(activity: CoinInfoActivity)
    fun inject(presenter: CoinInfoPresenter)
}