package com.rmnivnv.cryptomoon.view.fragments.coins

import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 11/07/2017.
 */
@Module
class CoinsModule(private val view: ICoins.View) {

    @Provides fun provideView() = view

    @Provides fun providePresenter(): ICoins.Presenter = CoinsPresenter()
}