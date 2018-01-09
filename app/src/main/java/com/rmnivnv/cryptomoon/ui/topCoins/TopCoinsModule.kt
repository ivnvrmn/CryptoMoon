package com.rmnivnv.cryptomoon.ui.topCoins

import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
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
    fun provideView(topCoinsFragment: TopCoinsFragment): ITopCoins.View = topCoinsFragment

    @Provides @PerFragment
    fun providePresenter(view: ITopCoins.View,
                         db: CMDatabase,
                         networkRequests: NetworkRequests,
                         coinsController: CoinsController,
                         resProvider: ResourceProvider,
                         pageController: PageController,
                         toaster: Toaster,
                         logger: Logger): ITopCoins.Presenter =
            TopCoinsPresenter(view, db, networkRequests, coinsController, resProvider, pageController, toaster, logger)
}