package com.rmnivnv.cryptomoon.ui.addTransaction

import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.model.db.DBController
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.Toaster
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 06/09/2017.
 */
@Module
class AddTransactionModule {

    @Provides @PerActivity
    fun provideView(addTransactionActivity: AddTransactionActivity): IAddTransaction.View {
        return addTransactionActivity
    }

    @Provides @PerActivity
    fun providePresenter(
        view: IAddTransaction.View,
        resourceProvider: ResourceProvider,
        dbController: DBController,
        toaster: Toaster
    ): IAddTransaction.Presenter {
        return AddTransactionPresenter(view, resourceProvider, dbController, toaster)
    }
}