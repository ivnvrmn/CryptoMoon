package com.rmnivnv.cryptomoon.view.topCoins

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rmnivnv on 02/09/2017.
 */
@Module
class TopCoinsModule(private val view: ITopCoins.View) {

    @Provides @Singleton fun provideView() = view

    @Provides @Singleton fun providePresenter(): ITopCoins.Presenter = TopCoinsPresenter()
}