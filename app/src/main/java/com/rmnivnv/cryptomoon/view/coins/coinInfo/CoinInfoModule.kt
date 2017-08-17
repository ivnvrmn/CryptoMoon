package com.rmnivnv.cryptomoon.view.coins.coinInfo

import dagger.Module
import dagger.Provides

/**
 * Created by ivanov_r on 17.08.2017.
 */
@Module
class CoinInfoModule(private val view: ICoinInfo.View) {

    @Provides fun provideView() = view

    @Provides fun providePresenter(): ICoinInfo.Presenter = CoinInfoPresenter()
}