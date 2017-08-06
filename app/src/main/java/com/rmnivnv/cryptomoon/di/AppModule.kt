package com.rmnivnv.cryptomoon.di

import android.arch.persistence.room.Room
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.DATABASE_NAME
import com.rmnivnv.cryptomoon.model.PreferencesManager
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.db.DBManager
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rmnivnv on 05/07/2017.
 */
@Module
class AppModule(private val app: MainApp) {

    @Provides @Singleton fun provideAppContext() = app

    @Provides @Singleton fun providePreferencesManager() = PreferencesManager(app)

    @Provides @Singleton fun provideDatabase() = Room.databaseBuilder(app, CMDatabase::class.java, DATABASE_NAME).build()

    @Provides @Singleton fun provideDBManager(db: CMDatabase) = DBManager(db)

    @Provides @Singleton fun provideResourceProvider() = ResourceProvider(app)
}