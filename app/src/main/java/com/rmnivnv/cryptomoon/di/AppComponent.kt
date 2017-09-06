package com.rmnivnv.cryptomoon.di

import android.app.Application
import com.rmnivnv.cryptomoon.MainApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by rmnivnv on 05/07/2017.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        AppModule::class, NetworkModule::class, ActivityBuilder::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: MainApp)
}