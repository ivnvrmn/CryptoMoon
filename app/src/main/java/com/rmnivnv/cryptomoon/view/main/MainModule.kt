package com.rmnivnv.cryptomoon.view.main

import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 06/07/2017.
 */
@Module
class MainModule(private val view: IMain.View) {

    @Provides fun provideView() = view

    @Provides fun providePresenter(): IMain.Presenter = MainPresenter()
}