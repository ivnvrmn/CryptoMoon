package com.rmnivnv.cryptomoon.ui.news

import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.Preferences
import com.rmnivnv.cryptomoon.utils.Logger
import dagger.Module
import dagger.Provides

/**
 * Created by ivanov_r on 26.09.2017.
 */
@Module
class NewsModule {

    @Provides @PerFragment
    fun provideView(newsFragment: NewsFragment): INews.View = newsFragment

    @Provides @PerFragment
    fun providePresenter(view: INews.View, preferences: Preferences, logger: Logger): INews.Presenter = NewsPresenter(view, preferences, logger)

}