package com.rmnivnv.cryptomoon.view.topCoins

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by rmnivnv on 02/09/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(TopCoinsModule::class))
interface TopCoinsComponent {
    fun inject(fragment: TopCoinsFragment)
    fun inject(presenter: TopCoinsPresenter)
    fun inject(adapter: TopCoinsAdapter)
}