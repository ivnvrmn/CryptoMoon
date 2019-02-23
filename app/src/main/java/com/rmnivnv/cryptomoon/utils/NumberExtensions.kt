package com.rmnivnv.cryptomoon.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import com.rmnivnv.cryptomoon.R

fun Float.toChangeColor(context: Context): Int {
    val color = when {
        this == 0f -> R.color.orange_dark
        this > 0 -> R.color.green
        else -> R.color.red
    }
    return ContextCompat.getColor(context, color)
}

fun Float.toChangeArrowDrawable(): Int {
    return when {
        this > 0 -> R.drawable.ic_arrow_drop_up_green
        this == 0f -> R.drawable.ic_remove_orange
        else -> R.drawable.ic_arrow_drop_down_red
    }
}