package com.rmnivnv.cryptomoon.base

import android.content.Context
import com.rmnivnv.cryptomoon.model.LocaleManager
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by rmnivnv on 03/01/2018.
 */

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }

}