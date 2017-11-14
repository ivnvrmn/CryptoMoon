package com.rmnivnv.cryptomoon.ui.coinAllocation

import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.PieMaker
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import dagger.Module
import dagger.Provides


@Module
class CoinAllocationModule {

    @Provides
    @PerActivity
    fun provideView(coinAllocationActivity: CoinAllocationActivity): ICoinAllocation.View = coinAllocationActivity

    @Provides
    @PerActivity
    fun providePresenter(view: ICoinAllocation.View,
                         pieMaker: PieMaker,
                         db: CMDatabase): ICoinAllocation.Presenter = CoinAllocationPresenter( view, pieMaker, db)
}