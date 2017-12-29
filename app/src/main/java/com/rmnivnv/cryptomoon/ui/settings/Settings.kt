package com.rmnivnv.cryptomoon.ui.settings

/**
 * Created by ivanov_r on 28.12.2017.
 */
interface Settings {

    interface View {
        fun setLanguage(language: String)
        fun showLanguageDialog()
    }

    interface Presenter {
        fun onCreate()
        fun onLanguageClicked()
        fun onStop()
    }

}