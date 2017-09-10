package com.rmnivnv.cryptomoon.ui.addCoin

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
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
    fun providePresenter(context: Context,
                                   view: IAddCoin.View,
                                   coinsController: CoinsController,
                                   networkRequests: NetworkRequests,
                                   resProvider: ResourceProvider,
                                   db: CMDatabase): IAddCoin.Presenter =
            AddCoinPresenter(context, view, coinsController, networkRequests, resProvider, db)
}