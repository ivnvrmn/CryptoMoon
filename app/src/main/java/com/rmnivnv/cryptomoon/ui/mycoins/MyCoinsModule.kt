package com.rmnivnv.cryptomoon.ui.mycoins

import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 11/07/2017.
 */
@Module
class MyCoinsModule {

    @Provides
    @PerFragment
    fun provideView(myCoinsFragment: MyCoinsFragment): MyCoinsContract.View = myCoinsFragment

    @Provides
    @PerFragment
    fun provideAdapter(): MyCoinsAdapter = MyCoinsAdapter()

    @Provides
    @PerFragment
    fun provideMyCoinsObservables(
        db: CMDatabase,
        pageController: PageController
    ): MyCoinsContract.Observables = MyCoinsObservables(db, pageController)

    @Provides
    @PerFragment
    fun provideMyCoinsApiRepository(
        cryptoCompareAPI: CryptoCompareAPI,
        resProvider: ResourceProvider
    ): MyCoinsContract.ApiRepository = MyCoinsApiRepository(cryptoCompareAPI, resProvider)

    @Provides
    @PerFragment
    fun provideMyCoinsDatabaseRepository(
        db: CMDatabase
    ): MyCoinsContract.DatabaseRepository = MyCoinsDatabaseRepository(db)

    @Provides
    @PerFragment
    fun providePresenter(
        view: MyCoinsContract.View,
        observables: MyCoinsContract.Observables,
        apiRepository: MyCoinsContract.ApiRepository,
        databaseRepository: MyCoinsContract.DatabaseRepository,
        logger: Logger
    ): MyCoinsContract.Presenter {
        return MyCoinsPresenter(
            view,
            observables,
            apiRepository,
            databaseRepository,
            logger
        )
    }

}