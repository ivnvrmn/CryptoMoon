package com.rmnivnv.cryptomoon.view.topCoins

import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 02/09/2017.
 */
@Module
class TopCoinsModule(private val view: ITopCoins.View) {

    @Provides fun provideView() = view

    @Provides fun providePresenter(): ITopCoins.Presenter = TopCoinsPresenter()
}