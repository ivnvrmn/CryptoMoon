package com.rmnivnv.cryptomoon.di

import android.arch.persistence.room.Room
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.DATABASE_NAME
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.db.DBController
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

    @Provides @Singleton fun provideDatabase() = Room.databaseBuilder(app, CMDatabase::class.java, DATABASE_NAME).build()

    @Provides @Singleton fun provideDBController(db: CMDatabase) = DBController(db)

    @Provides @Singleton fun provideResourceProvider() = ResourceProvider(app)

    @Provides @Singleton fun provideCoinsController(dbController: DBController) =
            CoinsController(dbController)
}