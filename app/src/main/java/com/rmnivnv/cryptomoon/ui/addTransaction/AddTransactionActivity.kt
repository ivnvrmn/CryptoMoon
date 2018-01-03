package com.rmnivnv.cryptomoon.ui.addTransaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.base.BaseActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import kotlinx.android.synthetic.main.activity_add_transaction.*
import java.util.*
import javax.inject.Inject

class AddTransactionActivity : BaseActivity(), IAddTransaction.View {

    @Inject lateinit var presenter: IAddTransaction.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        setupToolbar()
        setupObservables()
        setTotalValue("0.0")
        presenter.onCreate(intent.extras)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.add_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupObservables() {
        presenter.observeDateClicks(add_trans_trade_date.clicks())
        presenter.observeConfirmClicks(add_trans_confirm_btn.clicks())
        presenter.observeTradingPrice(add_trans_trading_price.textChanges())
        presenter.observeQuantity(add_trans_quantity.textChanges())
    }

    override fun setTitle(pair: String, price: String) {
        add_trans_pair.text = pair
        add_trans_current_price.text = price
    }

    override fun setTotalValue(value: String) {
        add_trans_total_value.text = value
    }

    override fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day -> presenter.onDatePicked(year, month, day) }
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this, dateSetListener,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DATE))
        datePickerDialog.setOnCancelListener { presenter.onDatePickerDialogCancelled() }
        datePickerDialog.show()
    }

    override fun setTransactionDate(date: String) {
        add_trans_trade_date.setText(date)
    }

    override fun closeActivity() {
        finish()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
