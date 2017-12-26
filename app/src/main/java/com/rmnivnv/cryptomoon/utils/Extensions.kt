package com.rmnivnv.cryptomoon.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by rmnivnv on 06/07/2017.
 */

fun Context.toastShort(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.logDebug(message: String) {
    Log.d("CryptoMoon debug", message)
}

fun Context.logError(message: String) {
    Log.e("CryptoMoon error", message)
}
