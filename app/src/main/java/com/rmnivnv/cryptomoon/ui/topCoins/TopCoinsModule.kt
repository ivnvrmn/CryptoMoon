package com.rmnivnv.cryptomoon.ui.topCoins

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerFragment
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
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
    fun providePresenter(context: Context,
                         view: ITopCoins.View,
                         db: CMDatabase,
                         networkRequests: NetworkRequests,
                         coinsController: CoinsController,
                         resProvider: ResourceProvider,
                         pageController: PageController): ITopCoins.Presenter =
            TopCoinsPresenter(context, view, db, networkRequests, coinsController, resProvider, pageController)
}