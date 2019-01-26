package com.rmnivnv.cryptomoon.utils

import java.text.DecimalFormat

fun String.to2DecimalPlaces(): String {
    val decimalFormat = DecimalFormat("#.##")
    return decimalFormat.format(this.toFloat())
}