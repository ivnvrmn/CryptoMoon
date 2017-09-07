package com.rmnivnv.cryptomoon.ui.addTransaction

import android.content.Context
import android.os.Bundle
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.FROM
import com.rmnivnv.cryptomoon.model.PRICE
import com.rmnivnv.cryptomoon.model.TO
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.toastShort
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 06/09/2017.
 */
class AddTransactionPresenter @Inject constructor(private val view: IAddTransaction.View,
                                                  private val context: Context,
                                                  private val resourceProvider: ResourceProvider) : IAddTransaction.Presenter {
    private var transactionDate = ""
    private var tradingPrice: Double = 0.0
        set(value) {
            field = value
            calculateTotalValue()
        }
    private var quantity: Double = 0.0
        set(value) {
            field = value
            calculateTotalValue()
        }

    private fun calculateTotalValue() {
        if (tradingPrice != 0.0 && quantity != 0.0) {
            view.setTotalValue((tradingPrice * quantity).toString())
        }
    }

    override fun onCreate(extras: Bundle) {
        if (extras[FROM] != null && extras[TO] != null && extras[PRICE] != null) {
            view.setTitle("${extras.getString(FROM)} / ${extras.getString(TO)}", extras.getString(PRICE))
        }
    }

    override fun onTradingPriceTextChanged(text: String) {
        if (text.isNotEmpty()) tradingPrice = text.toDouble()
        else {
            tradingPrice = 0.0
            view.setTotalValue("0.0")
        }
    }

    override fun onQuantityTextChanged(text: String) {
        if (text.isNotEmpty()) quantity = text.toDouble()
        else {
            quantity = 0.0
            view.setTotalValue("0.0")
        }
    }

    override fun onDatePicked(year: Int, month: Int, day: Int) {
        val pickedDate = Calendar.getInstance()
        pickedDate.set(Calendar.YEAR, year)
        pickedDate.set(Calendar.MONTH, month)
        pickedDate.set(Calendar.DATE, day)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        setDate(format.format(pickedDate.time))
    }

    private fun setDate(date: String) {
        transactionDate = date
        view.setTransactionDate(transactionDate)
    }

    override fun onDatePickerDialogCancelled() = setDate("")

    override fun onConfirmClicked() {
        if (allFieldsAreFilled()) {
        } else if (tradingPrice == 0.0 && quantity == 0.0 && transactionDate.isEmpty()) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_all_fields))
        } else if (transactionDate.isEmpty()) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_date))
        } else if (tradingPrice == 0.0) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_price))
        } else {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_quantity))
        }
    }

    private fun allFieldsAreFilled() = tradingPrice != 0.0 && quantity != 0.0 && transactionDate.isNotEmpty()
}