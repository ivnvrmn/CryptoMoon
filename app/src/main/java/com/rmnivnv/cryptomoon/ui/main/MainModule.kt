package com.rmnivnv.cryptomoon.ui.main

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.MultiSelector
import com.rmnivnv.cryptomoon.model.PageController
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 06/07/2017.
 */
@Module
class MainModule {

    @Provides @PerActivity
    fun provideView(mainActivity: MainActivity): IMain.View = mainActivity

    @Provides @PerActivity
    fun providePresenter(context: Context, view: IMain.View, multiSelector: MultiSelector,
                                   pageController: PageController): IMain.Presenter =
            MainPresenter(context, view, multiSelector, pageController)
}