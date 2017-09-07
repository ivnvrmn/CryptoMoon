package com.rmnivnv.cryptomoon.ui.addTransaction

import android.content.Context
import com.rmnivnv.cryptomoon.di.PerActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.Module
import dagger.Provides

/**
 * Created by rmnivnv on 06/09/2017.
 */
@Module
class AddTransactionModule {

    @Provides @PerActivity
    fun provideView(addTransactionActivity: AddTransactionActivity): IAddTransaction.View = addTransactionActivity

    @Provides @PerActivity
    fun providePresenter(view: IAddTransaction.View, context: Context, resourceProvider: ResourceProvider): IAddTransaction.Presenter =
            AddTransactionPresenter(view, context, resourceProvider)
}