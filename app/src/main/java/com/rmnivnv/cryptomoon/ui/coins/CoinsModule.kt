package com.rmnivnv.cryptomoon.ui.coins

import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.Toaster
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 11/07/2017.
 */
@Module
class CoinsModule {

    @Provides @PerFragment
    fun provideView(coinsFragment: CoinsFragment): ICoins.View = coinsFragment

    @Provides @PerFragment
    fun providePresenter(view: ICoins.View,
                         networkRequests: NetworkRequests,
                         coinsController: CoinsController,
                         db: CMDatabase,
                         resProvider: ResourceProvider,
                         pageController: PageController,
                         multiSelector: MultiSelector,
                         holdingsHandler: HoldingsHandler,
                         logger: Logger,
                         toaster: Toaster,
                         preferences: Preferences): ICoins.Presenter =
            CoinsPresenter(view, networkRequests, coinsController, db, resProvider,
                    pageController, multiSelector, holdingsHandler, logger, toaster, preferences)
}