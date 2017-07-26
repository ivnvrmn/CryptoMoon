package com.rmnivnv.cryptomoon.view.coins.addCoin

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by rmnivnv on 27/07/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(AddCoinModule::class))
interface AddCoinComponent {
    fun inject(activity: AddCoinActivity)
    fun inject(presenter: AddCoinPresenter)
}