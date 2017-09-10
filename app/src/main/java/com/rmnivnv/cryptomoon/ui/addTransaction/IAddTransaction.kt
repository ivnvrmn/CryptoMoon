package com.rmnivnv.cryptomoon.ui.addTransaction

import android.os.Bundle

/**
 * Created by rmnivnv on 06/09/2017.
 */
interface IAddTransaction {

    interface View {
        fun setTitle(pair: String, price: String)
        fun setTotalValue(value: String)
        fun showDatePickerDialog()
        fun setTransactionDate(date: String)
        fun closeActivity()
    }

    interface Presenter {
        fun onCreate(extras: Bundle)
        fun onTradingPriceTextChanged(text: String)
        fun onQuantityTextChanged(text: String)
        fun onDatePicked(year: Int, month: Int, day: Int)
        fun onConfirmClicked()
        fun onDatePickerDialogCancelled()
    }
}