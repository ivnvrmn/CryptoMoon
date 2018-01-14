package com.rmnivnv.cryptomoon.utils

import android.content.Context

/**
 * Created by ivanov_r on 09.01.2018.
 */
class Logger(private val context: Context) {

    fun logDebug(message: String) = context.logDebug(message)

    fun logError(message: String) = context.logError(message)

}