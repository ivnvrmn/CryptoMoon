package com.rmnivnv.cryptomoon.ui.addTransaction

import android.os.Bundle
import io.reactivex.Observable

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
        fun observeTradingPrice(observable: Observable<CharSequence>)
        fun observeQuantity(observable: Observable<CharSequence>)
        fun observeDateClicks(observable: Observable<Unit>)
        fun observeConfirmClicks(observable: Observable<Unit>)
        fun onDatePicked(year: Int, month: Int, day: Int)
        fun onDatePickerDialogCancelled()
        fun onDestroy()
    }
}