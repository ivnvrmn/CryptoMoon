package com.rmnivnv.cryptomoon.ui.settings

import android.content.Context
import android.os.Build
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.Prefs
import com.rmnivnv.cryptomoon.model.rxbus.LanguageChanged
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 28.12.2017.
 */
class SettingsPresenter @Inject constructor(
        private val view: Settings.View,
        private val context: Context,
        private val resourceProvider: ResourceProvider) : Settings.Presenter {



    private val prefs = Prefs(context)
    private val disposable = CompositeDisposable()

    override fun onCreate() {
        initLanguage()
        setRxEventsListeners()
    }

    private fun initLanguage() {
        if (prefs.language.isEmpty()) setLanguageByPhoneConfig()
        view.setLanguage(prefs.language)
    }

    private fun setLanguageByPhoneConfig() {
        when (getLocaleFromConfiguration()) {
            "ru" -> prefs.language = resourceProvider.getString(R.string.russian)
            else -> prefs.language = resourceProvider.getString(R.string.english)
        }
    }

    private fun getLocaleFromConfiguration() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) context.resources.configuration.locales[0].language
            else context.resources.configuration.locale.language

    private fun setRxEventsListeners() {
        disposable.add(RxBus.listen(LanguageChanged::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onLanguageChanged(it.language) })
    }

    private fun onLanguageChanged(language: String) {

    }

    override fun onLanguageClicked() {
        view.showLanguageDialog()
    }

    override fun onStop() {
        disposable.clear()
    }
}