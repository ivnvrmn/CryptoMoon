package com.rmnivnv.cryptomoon.utils

import android.content.Context

/**
 * Created by ivanov_r on 09.01.2018.
 */
class LoggerImpl(private val context: Context) : Logger {

    override fun logDebug(message: String) = context.logDebug(message)

    override fun logError(message: String) = context.logError(message)

}