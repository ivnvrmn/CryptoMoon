package com.rmnivnv.cryptomoon.utils

import android.content.Context

/**
 * Created by ivanov_r on 09.01.2018.
 */
class ToasterImpl(private val context: Context) : Toaster {
    override fun toastShort(message: CharSequence) = context.toastShort(message)
}