package com.rmnivnv.cryptomoon.ui.topcoins

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.Toaster
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 02/09/2017.
 */
@Module
class TopCoinsModule {

    @Provides
    @PerFragment
    fun provideView(
        topCoinsFragment: TopCoinsFragment
    ): TopCoinsContract.View = topCoinsFragment

    @Provides
    @PerFragment
    fun provideAdapter(
        presenter: TopCoinsContract.Presenter
    ) : TopCoinsAdapter = TopCoinsAdapter(presenter)

    @Provides
    @PerFragment
    fun provideApiRepository(
        cryptoCompareAPI: CryptoCompareAPI,
        resProvider: ResourceProvider
    ): TopCoinsContract.ApiRepository = TopCoinsApiRepository(cryptoCompareAPI, resProvider)

    @Provides
    @PerFragment
    fun provideTopCoinsDbRepository(
        db: CMDatabase
    ): TopCoinsContract.DatabaseRepository = TopCoinsDatabaseRepository(db)

    @Provides
    @PerFragment
    fun provideTopCoinsObservables(
        db: CMDatabase,
        pageController: PageController
    ): TopCoinsContract.Observables = TopCoinsObservables(db, pageController)

    @Provides
    @PerFragment
    fun provideTopCoinsResourceProvider(
        context: Context
    ): TopCoinsContract.ResourceProvider = TopCoinsResourceProvider(context)

    @Provides
    @PerFragment
    fun providePresenter(
        view: TopCoinsContract.View,
        observables: TopCoinsContract.Observables,
        repository: TopCoinsContract.ApiRepository,
        dbRepository: TopCoinsContract.DatabaseRepository,
        resProvider: TopCoinsContract.ResourceProvider,
        toaster: Toaster,
        logger: Logger
    ): TopCoinsContract.Presenter {
        return TopCoinsPresenter(
            view,
            observables,
            repository,
            dbRepository,
            resProvider,
            toaster,
            logger
        )
    }
}