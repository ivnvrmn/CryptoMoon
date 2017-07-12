package com.rmnivnv.cryptomoon

import android.app.Application
import com.rmnivnv.cryptomoon.di.AppComponent
import com.rmnivnv.cryptomoon.di.AppModule
import com.rmnivnv.cryptomoon.di.DaggerAppComponent

/**
 * Created by rmnivnv on 05/07/2017.
 */


class MainApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    fun getAppComponent() = component
}