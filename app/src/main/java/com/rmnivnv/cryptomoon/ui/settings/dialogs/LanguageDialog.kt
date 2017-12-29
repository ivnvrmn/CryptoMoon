package com.rmnivnv.cryptomoon.ui.settings.dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.Prefs
import com.rmnivnv.cryptomoon.model.rxbus.LanguageChanged
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import kotlinx.android.synthetic.main.language_dialog.*

/**
 * Created by ivanov_r on 28.12.2017.
 */
class LanguageDialog : DialogFragment() {

    private lateinit var prefs: Prefs
    private lateinit var selectedLang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(activity?.applicationContext!!)
        selectedLang = prefs.language
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.language_dialog, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lang_dialog_cancel.setOnClickListener { dismiss() }
        setCheckedBtn()
        radio_group.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.lang_dialog_english -> {
                }
                R.id.lang_dialog_russian -> {
                }
            }
        }
    }

    private fun setCheckedBtn() {
        when (prefs.language) {
            activity?.getString(R.string.english) -> lang_dialog_english.isChecked = true
            activity?.getString(R.string.russian) -> lang_dialog_russian.isChecked = true
        }
    }

    private fun onLanguageSelected(stringId: Int, language: String) {
        prefs.language = activity?.getString(stringId)!!
        RxBus.publish(LanguageChanged(language))
        dismiss()
    }

}