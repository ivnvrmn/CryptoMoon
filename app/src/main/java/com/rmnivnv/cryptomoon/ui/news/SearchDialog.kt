package com.rmnivnv.cryptomoon.ui.news

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.Prefs
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.rxbus.SearchHashTagUpdated
import kotlinx.android.synthetic.main.search_dialog.*

/**
 * Created by rmnivnv on 09/12/2017.
 */
class SearchDialog : DialogFragment() {

    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(activity?.applicationContext!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.search_dialog, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_cancel.setOnClickListener { dismiss() }
        search_ok.setOnClickListener { onOkClicked() }
        search_text.setText(prefs.searchHashTag)
        search_text.setSelection(search_text.text.length)
    }

    private fun onOkClicked() {
        prefs.searchHashTag =  search_text.text.toString()
        RxBus.publish(SearchHashTagUpdated())
        dismiss()
    }
}