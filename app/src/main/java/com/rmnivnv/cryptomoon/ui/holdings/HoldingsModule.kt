package com.rmnivnv.cryptomoon.ui.holdings

import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.db.CMDatabase
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
    fun providePresenter(view: IHoldings.View, db: CMDatabase): IHoldings.Presenter = HoldingsPresenter(view, db)

}