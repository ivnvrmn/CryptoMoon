package com.rmnivnv.cryptomoon.view.coins

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by rmnivnv on 11/07/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(CoinsModule::class))
interface CoinsComponent {
    fun inject(fragment: CoinsFragment)
    fun inject(presenter: CoinsPresenter)
    fun inject(adapter: CoinsListAdapter)
}