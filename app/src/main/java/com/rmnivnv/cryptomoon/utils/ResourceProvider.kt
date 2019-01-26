package com.rmnivnv.cryptomoon.utils

import android.graphics.drawable.Drawable

interface ResourceProvider {
    fun getString(id: Int): String
    fun getDrawable(id: Int): Drawable?
    fun getColor(id: Int): Int
    fun getStringArray(id: Int): Array<String>
}
