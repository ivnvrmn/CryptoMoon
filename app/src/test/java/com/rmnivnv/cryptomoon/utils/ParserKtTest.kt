package com.rmnivnv.cryptomoon.utils

import com.rmnivnv.cryptomoon.R
import org.amshove.kluent.`should equal to`

import org.junit.Test

/**
 * Created by ivanov_r on 08.09.2017.
 */
class ParserKtTest {

    @Test
    fun `Given positive double When getChangeColor Then return green color`() = getChangeColor(32.0) `should equal to` R.color.green

    @Test
    fun `Given negative double When getChangeColor Then return red color`() = getChangeColor(-32.0) `should equal to` R.color.red

    @Test
    fun `Given zero double When getChangeColor Then return orange dark color`() = getChangeColor(0.0) `should equal to` R.color.orange_dark
}