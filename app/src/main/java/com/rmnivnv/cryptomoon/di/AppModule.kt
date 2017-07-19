package com.rmnivnv.cryptomoon.di

import android.content.Context
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.CRYPTOMOON_PREFS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rmnivnv on 05/07/2017.
 */
@Module
class AppModule(private val app: MainApp) {

    @Provides @Singleton fun provideAppContext() = app

    @Provides @Singleton fun provideSharedPreferences() = app.getSharedPreferences(CRYPTOMOON_PREFS, Context.MODE_PRIVATE)
}