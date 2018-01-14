package com.rmnivnv.cryptomoon.ui.main

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.rxbus.CoinsSortMethodUpdated
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import kotlinx.android.synthetic.main.sort_dialog.*

/**
 * Created by rmnivnv on 23/12/2017.
 */
class SortDialog : DialogFragment() {

    companion object {
        private val NOTHING_SELECTED = -1
        val SORT_BY_NAME = "name"
        val SORT_BY_PRICE_INCREASE = "price_decrease"
        val SORT_BY_PRICE_DECREASE = "price_increase"
        val SORT_BY_24H_PRICE_INCREASE = "24h_price_increase"
        val SORT_BY_24H_PRICE_DECREASE = "24h_price_decrease"
    }

    private var selectedSort: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedSort = arguments?.getString("sort")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.sort_dialog, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sort_cancel.setOnClickListener { dismiss() }
        sort_ok.setOnClickListener { onOkClicked() }
        setCheckedButton()
        radio_group.setOnCheckedChangeListener { _, id ->
            when (id) {
                NOTHING_SELECTED -> {}
                R.id.sort_dialog_by_name -> selectedSort = SORT_BY_NAME
                R.id.sort_dialog_by_price_increase -> selectedSort = SORT_BY_PRICE_INCREASE
                R.id.sort_dialog_by_price_decrease -> selectedSort = SORT_BY_PRICE_DECREASE
                R.id.sort_dialog_by_24h_price_increase -> selectedSort = SORT_BY_24H_PRICE_INCREASE
                R.id.sort_dialog_by_24h_price_decrease -> selectedSort = SORT_BY_24H_PRICE_DECREASE
            }
        }
    }

    private fun onOkClicked() {
        RxBus.publish(CoinsSortMethodUpdated(selectedSort))
        dismiss()
    }

    private fun setCheckedButton() {
        when (selectedSort) {
            SORT_BY_NAME -> sort_dialog_by_name.isChecked = true
            SORT_BY_PRICE_INCREASE -> sort_dialog_by_price_increase.isChecked = true
            SORT_BY_PRICE_DECREASE -> sort_dialog_by_price_decrease.isChecked = true
            SORT_BY_24H_PRICE_INCREASE -> sort_dialog_by_24h_price_increase.isChecked = true
            SORT_BY_24H_PRICE_DECREASE -> sort_dialog_by_24h_price_decrease.isChecked = true
        }
    }
}