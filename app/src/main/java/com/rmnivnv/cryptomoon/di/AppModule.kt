package com.rmnivnv.cryptomoon.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.db.DBController
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.ResourceProviderImpl
import com.rmnivnv.cryptomoon.utils.Toaster
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rmnivnv on 05/07/2017.
 */
@Module
class AppModule {

    @Provides @Singleton
    fun provideAppContext(application: Application): Context = application

    @Provides @Singleton
    fun provideDatabase(application: Application): CMDatabase {
        return Room.databaseBuilder(application, CMDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides @Singleton
    fun provideDBController(db: CMDatabase) = DBController(db)

    @Provides @Singleton
    fun provideResourceProvider(application: Application): ResourceProvider {
        return ResourceProviderImpl(application)
    }

    @Provides @Singleton
    fun provideCoinsController(dbController: DBController, db: CMDatabase) = CoinsController(dbController, db)

    @Provides @Singleton
    fun provideMultiSelector(resourceProvider: ResourceProvider) = MultiSelector(resourceProvider)

    @Provides @Singleton
    fun providePageController() = PageController()

    @Provides @Singleton
    fun provideGraphMaker(resourceProvider: ResourceProvider) = GraphMaker(resourceProvider)

    @Provides @Singleton
    fun providePieMaker(
        resourceProvider: ResourceProvider,
        holdingsHandler: HoldingsHandler
    ): PieMaker {
        return PieMaker(resourceProvider, holdingsHandler)
    }

    @Provides @Singleton
    fun provideHoldingsHandler(db: CMDatabase) = HoldingsHandler(db)

    @Provides @Singleton
    fun provideLogger(context: Context) = Logger(context)

    @Provides @Singleton
    fun provideToaster(context: Context) = Toaster(context)

    @Provides @Singleton
    fun providePreferences(context: Context) = Preferences(context)
}