package com.rmnivnv.cryptomoon.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*


/**
 * Created by rmnivnv on 02/01/2018.
 */

//todo refactor this with injected Preferences and Context
class LocaleManager {

    companion object {

        val ENGLISH = "en"
        val RUSSIAN = "ru"

        fun setLocale(context: Context): Context = updateResources(context, getLanguage(context))

        fun setNewLocale(context: Context, language: String): Context {
            persistLanguage(context, language)
            return updateResources(context, language)
        }

        private fun getLanguage(context: Context): String {
            val prefs = Preferences(context)
            return prefs.language
        }

        @SuppressLint("ApplySharedPref")
        private fun persistLanguage(context: Context, language: String) {
            val prefs = Preferences(context)
            prefs.language = language
        }

        private fun updateResources(context: Context, language: String): Context {
            var contextChange = context
            val locale = Locale(language)
            Locale.setDefault(locale)

            val res = contextChange.resources
            val config = Configuration(res.configuration)
            config.setLocale(locale)
            contextChange = contextChange.createConfigurationContext(config)
            return contextChange
        }

        fun getLocale(res: Resources): Locale {
            val config = res.configuration
            return if (Build.VERSION.SDK_INT >= 24) config.locales[0] else config.locale
        }
    }

}