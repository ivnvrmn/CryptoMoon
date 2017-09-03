package com.rmnivnv.cryptomoon.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.rmnivnv.cryptomoon.MainApp

/**
 * Created by rmnivnv on 06/07/2017.
 */


val AppCompatActivity.app: MainApp
    get() = application as MainApp

val Fragment.app: MainApp
    get() = activity.application as MainApp

fun Context.toastShort(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.logDebug(message: String) {
    Log.d("CM debug", message)
}
