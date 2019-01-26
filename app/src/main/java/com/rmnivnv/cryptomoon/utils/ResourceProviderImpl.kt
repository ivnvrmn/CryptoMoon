package com.rmnivnv.cryptomoon.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

/**
 * Created by rmnivnv on 01/08/2017.
 */
class ResourceProviderImpl(val context: Context) : ResourceProvider {

    override fun getString(id: Int): String = context.getString(id)

    override fun getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)

    override fun getColor(id: Int) = ContextCompat.getColor(context, id)

    override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)
}