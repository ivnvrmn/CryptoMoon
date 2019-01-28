package com.rmnivnv.cryptomoon.utils

import java.text.DecimalFormat

fun String.to4DecimalPlaces(): String {
    val decimalFormat = DecimalFormat("#.####")
    return decimalFormat.format(this.toFloat())
}