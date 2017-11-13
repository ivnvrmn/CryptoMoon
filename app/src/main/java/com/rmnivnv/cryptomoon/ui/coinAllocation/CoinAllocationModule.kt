package com.rmnivnv.cryptomoon.ui.coinAllocation

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.HoldingsHandler
import com.rmnivnv.cryptomoon.model.PieMaker
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.db.DBController
import com.rmnivnv.cryptomoon.ui.addTransaction.AddTransactionActivity
import com.rmnivnv.cryptomoon.ui.addTransaction.AddTransactionPresenter
import com.rmnivnv.cryptomoon.ui.addTransaction.IAddTransaction
import com.rmnivnv.cryptomoon.utils.ResourceProvider
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
                         db: CMDatabase): ICoinAllocation.Presenter =
            CoinAllocationPresenter( view, pieMaker, db)
}