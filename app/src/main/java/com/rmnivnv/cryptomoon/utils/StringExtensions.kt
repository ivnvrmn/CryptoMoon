package com.rmnivnv.cryptomoon.utils

import com.rmnivnv.cryptomoon.di.CRYPTO_COMPARE_IMAGE_URL

private const val SIGN_PERCENT = "%"
private const val SIGN_PLUS = "+"
private const val BLANK = ""

fun String.addPercentSign(changePercent: Float): String =
    "${getChangePercentSign(changePercent)}$this$SIGN_PERCENT"

private fun getChangePercentSign(changePct: Float): String {
    return when {
        changePct > 0f -> SIGN_PLUS
        else -> BLANK
    }
}

fun String.toCryptoCompareImageUrl(): String = "$CRYPTO_COMPARE_IMAGE_URL$this"