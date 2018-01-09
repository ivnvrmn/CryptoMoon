package com.rmnivnv.cryptomoon.ui.holdings

import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.Toaster
import dagger.Module
import dagger.Provides

/**
 * Created by ivanov_r on 13.09.2017.
 */
@Module
class HoldingsModule {

    @Provides @PerActivity
    fun provideView(holdingsActivity: HoldingsActivity): IHoldings.View = holdingsActivity

    @Provides @PerActivity
    fun providePresenter(view: IHoldings.View,
                         db: CMDatabase,
                         resourceProvider: ResourceProvider,
                         toaster: Toaster): IHoldings.Presenter =
            HoldingsPresenter(view, db, resourceProvider, toaster)

}