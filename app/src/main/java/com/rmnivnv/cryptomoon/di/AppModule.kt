package com.rmnivnv.cryptomoon.di

import com.rmnivnv.cryptomoon.MainApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rmnivnv on 05/07/2017.
 */
@Module
class AppModule(private val app: MainApp) {

    @Provides @Singleton fun provideAppContext() = app


}