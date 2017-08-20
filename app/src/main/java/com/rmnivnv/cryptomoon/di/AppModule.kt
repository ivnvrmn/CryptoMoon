package com.rmnivnv.cryptomoon.di

import android.arch.persistence.room.Room
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.*
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

    @Provides @Singleton fun provideCoinsController(dbController: DBController, db: CMDatabase) =
            CoinsController(dbController, db)

    @Provides @Singleton fun provideMultiSelector(resourceProvider: ResourceProvider) =
            MultiSelector(resourceProvider)

    @Provides @Singleton fun providePageController() = PageController()

    @Provides @Singleton fun provideGraphMaker(resourceProvider: ResourceProvider) =
            GraphMaker(resourceProvider)
}