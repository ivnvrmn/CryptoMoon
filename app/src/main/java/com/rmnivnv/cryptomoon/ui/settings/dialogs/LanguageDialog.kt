package com.rmnivnv.cryptomoon.ui.settings.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.LocaleManager
import com.rmnivnv.cryptomoon.model.rxbus.LanguageChanged
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import kotlinx.android.synthetic.main.language_dialog.*

/**
 * Created by ivanov_r on 28.12.2017.
 */
class LanguageDialog : DialogFragment() {

    private var selectedLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedLang = arguments?.getString("lang")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.language_dialog, container, false)!!

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lang_dialog_cancel.setOnClickListener { dismiss() }
        setCheckedBtn()
        radio_group.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.lang_dialog_english -> onLanguageSelected(LocaleManager.ENGLISH)
                R.id.lang_dialog_russian -> onLanguageSelected(LocaleManager.RUSSIAN)
            }
        }
    }

    private fun setCheckedBtn() {
        when (selectedLang) {
            LocaleManager.ENGLISH -> lang_dialog_english.isChecked = true
            LocaleManager.RUSSIAN -> lang_dialog_russian.isChecked = true
        }
    }

    private fun onLanguageSelected(language: String) {
        RxBus.publish(LanguageChanged(language))
        dismiss()
    }

}