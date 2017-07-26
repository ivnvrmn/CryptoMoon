package com.rmnivnv.cryptomoon.view.main

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by rmnivnv on 06/07/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(activity: MainActivity)
    fun inject(presenter: MainPresenter)
}