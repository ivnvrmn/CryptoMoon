package com.rmnivnv.cryptomoon.ui.addTransaction

import android.content.Context
import android.os.Bundle
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.FROM
import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.PRICE
import com.rmnivnv.cryptomoon.model.TO
import com.rmnivnv.cryptomoon.model.db.DBController
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.doubleFromString
import com.rmnivnv.cryptomoon.utils.toastShort
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 06/09/2017.
 */
class AddTransactionPresenter @Inject constructor(private val view: IAddTransaction.View,
                                                  private val context: Context,
                                                  private val resourceProvider: ResourceProvider,
                                                  private val dbController: DBController) : IAddTransaction.Presenter {
    private var from = ""
    private var to = ""
    private var price = 0.0
    private var transactionDate: Date? = null
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
            from = extras.getString(FROM)
            to = extras.getString(TO)
            price = doubleFromString(extras.getString(PRICE).substring(2))
            view.setTitle("$from / $to", extras.getString(PRICE))
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
        transactionDate = pickedDate.time
        setDate()
    }

    private fun setDate() {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        view.setTransactionDate(format.format(transactionDate))
    }

    override fun onDatePickerDialogCancelled() {
        transactionDate = null
    }

    override fun onConfirmClicked() {
        if (allFieldsAreFilled()) {
            saveHoldings()
        } else if (tradingPrice == 0.0 && quantity == 0.0 && transactionDate == null) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_all_fields))
        } else if (transactionDate == null) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_date))
        } else if (tradingPrice == 0.0) {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_price))
        } else {
            context.toastShort(resourceProvider.getString(R.string.add_trans_fill_quantity))
        }
    }

    private fun allFieldsAreFilled() = tradingPrice != 0.0 && quantity != 0.0 && transactionDate != null

    private fun saveHoldings() {
        dbController.saveHoldingData(HoldingData(from, to, quantity, price, transactionDate?.time))
        context.toastShort(resourceProvider.getString(R.string.transaction_added))
        view.closeActivity()
    }
}