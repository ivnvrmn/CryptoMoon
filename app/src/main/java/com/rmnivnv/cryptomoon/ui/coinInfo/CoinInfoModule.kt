package com.rmnivnv.cryptomoon.ui.coinInfo

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.Module
import dagger.Provides

/**
 * Created by ivanov_r on 17.08.2017.
 */
@Module
class CoinInfoModule {

    @Provides
    @PerActivity
    fun provideView(coinInfoActivity: CoinInfoActivity): CoinInfoContract.View = coinInfoActivity

    @Provides
    @PerActivity
    fun provideObservables(): CoinInfoContract.Observables = CoinInfoObervables()

    @Provides
    @PerActivity
    fun provideApiRepository(
        cryptoCompareAPI: CryptoCompareAPI,
        resProvider: ResourceProvider
    ): CoinInfoContract.ApiRepository = CoinInfoApiRepository(cryptoCompareAPI, resProvider)

    @Provides
    @PerActivity
    fun provideResourceProvider(
        context: Context
    ): CoinInfoContract.ResourceProvider = CoinInfoResourceProvider(context)

    @Provides
    @PerActivity
    fun providePresenter(
        view: CoinInfoContract.View,
        observables: CoinInfoContract.Observables,
        apiRepository: CoinInfoContract.ApiRepository,
        resourceProvider: CoinInfoContract.ResourceProvider,
        logger: Logger
    ): CoinInfoContract.Presenter {
        return CoinInfoPresenter(
            view,
            observables,
            apiRepository,
            resourceProvider,
            logger
        )
    }
}