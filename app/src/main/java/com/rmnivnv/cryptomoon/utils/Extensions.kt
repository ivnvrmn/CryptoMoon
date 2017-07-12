package com.rmnivnv.cryptomoon.utils

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.rmnivnv.cryptomoon.MainApp

/**
 * Created by rmnivnv on 06/07/2017.
 */


val AppCompatActivity.app: MainApp
    get() = application as MainApp

val Fragment.app: MainApp
    get() = activity.application as MainApp