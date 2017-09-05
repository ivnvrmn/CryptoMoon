package com.rmnivnv.cryptomoon.ui.coins

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.MultiSelector
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
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
    fun providePresenter(context: Context,
                         view: ICoins.View,
                         networkRequests: NetworkRequests,
                         coinsController: CoinsController,
                         db: CMDatabase,
                         resProvider: ResourceProvider,
                         pageController: PageController,
                         multiSelector: MultiSelector): ICoins.Presenter =
            CoinsPresenter(context, view, networkRequests, coinsController, db, resProvider, pageController, multiSelector)
}