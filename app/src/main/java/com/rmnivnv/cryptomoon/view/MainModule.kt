package com.rmnivnv.cryptomoon.view

import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 06/07/2017.
 */
@Module
class MainModule(private val view: MainInterface.View) {

    @Provides fun provideView() = view

    @Provides fun providePresenter(): MainInterface.Presenter = MainPresenter()
}