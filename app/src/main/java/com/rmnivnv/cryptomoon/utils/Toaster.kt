package com.rmnivnv.cryptomoon.utils

import android.content.Context

/**
 * Created by ivanov_r on 09.01.2018.
 */
class Toaster(private val context: Context) {

    fun toastShort(message: String) = context.toastShort(message)

    fun toastLong(message: String) = context.toastLong(message)

}