package com.rmnivnv.cryptomoon.ui.addCoin

import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.Toaster
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 27/07/2017.
 */
@Module
class AddCoinModule {

    @Provides @PerActivity
    fun provideView(addCoinActivity: AddCoinActivity): IAddCoin.View = addCoinActivity

    @Provides @PerActivity
    fun providePresenter(view: IAddCoin.View,
                         coinsController: CoinsController,
                         networkRequests: NetworkRequests,
                         resProvider: ResourceProvider,
                         db: CMDatabase,
                         toaster: Toaster): IAddCoin.Presenter =
            AddCoinPresenter(view, coinsController, networkRequests, resProvider, db, toaster)
}