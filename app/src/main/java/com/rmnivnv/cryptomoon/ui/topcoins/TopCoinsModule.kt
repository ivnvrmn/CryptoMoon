package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.CoinMarketCapApi
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
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

    @Provides @PerFragment
    fun provideView(topCoinsFragment: TopCoinsFragment): TopCoinsContract.View = topCoinsFragment

    @Provides @PerFragment
    fun provideAdapter(
        presenter: TopCoinsContract.Presenter,
        resProvider: ResourceProvider,
        coinsController: CoinsController
    ) : TopCoinsAdapter {
        return TopCoinsAdapter(presenter, resProvider, coinsController)
    }

    @Provides @PerFragment
    fun provideRepository(api: CoinMarketCapApi): TopCoinsRepository {
        return TopCoinsRepositoryImpl(api)
    }

    @Provides @PerFragment
    fun providePresenter(
            view: TopCoinsContract.View,
            db: CMDatabase,
            networkRequests: NetworkRequests,
            repository: TopCoinsRepository,
            coinsController: CoinsController,
            resProvider: ResourceProvider,
            pageController: PageController,
            toaster: Toaster,
            logger: Logger
    ): TopCoinsContract.Presenter {
        return TopCoinsPresenter(
            view,
            db,
            networkRequests,
            repository,
            coinsController,
            resProvider,
            pageController,
            toaster,
            logger
        )
    }

}