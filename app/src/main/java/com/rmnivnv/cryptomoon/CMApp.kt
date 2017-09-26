package com.rmnivnv.cryptomoon

import android.app.Activity
import android.app.Application
import com.rmnivnv.cryptomoon.di.DaggerAppComponent
import com.twitter.sdk.android.core.Twitter
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by rmnivnv on 05/07/2017.
 */


class CMApp : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
        Twitter.initialize(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}