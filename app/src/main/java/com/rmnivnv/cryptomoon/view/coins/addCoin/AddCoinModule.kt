package com.rmnivnv.cryptomoon.view.coins.addCoin

import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 27/07/2017.
 */
@Module
class AddCoinModule(private val view: IAddCoin.View) {

    @Provides fun provideView() = view

    @Provides fun providePresenter() : IAddCoin.Presenter = AddCoinPresenter()

}