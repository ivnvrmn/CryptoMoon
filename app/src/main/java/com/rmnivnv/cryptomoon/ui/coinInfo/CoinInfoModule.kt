package com.rmnivnv.cryptomoon.ui.coinInfo

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.GraphMaker
import com.rmnivnv.cryptomoon.model.HoldingsHandler
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.Module
import dagger.Provides

/**
 * Created by ivanov_r on 17.08.2017.
 */
@Module
class CoinInfoModule {

    @Provides @PerActivity
    fun provideView(coinInfoActivity: CoinInfoActivity): ICoinInfo.View = coinInfoActivity

    @Provides @PerActivity
    fun providePresenter(context: Context,
                         view: ICoinInfo.View,
                         coinsController: CoinsController,
                         networkRequests: NetworkRequests,
                         graphMaker: GraphMaker,
                         holdingsHandler: HoldingsHandler,
                         resourceProvider: ResourceProvider): ICoinInfo.Presenter =
            CoinInfoPresenter(context, view, coinsController, networkRequests, graphMaker, holdingsHandler, resourceProvider)

}