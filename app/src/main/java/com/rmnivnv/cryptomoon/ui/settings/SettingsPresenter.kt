package com.rmnivnv.cryptomoon.ui.settings

import android.content.Context
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.LocaleManager
import com.rmnivnv.cryptomoon.model.Preferences
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
        private val resourceProvider: ResourceProvider,
        private val preferences: Preferences) : Settings.Presenter {

    private val disposable = CompositeDisposable()

    override fun onCreate() {
        initLanguage()
        setRxEventsListeners()
    }

    private fun initLanguage() {
        if (preferences.language.isEmpty()) preferences.language = LocaleManager.getLocale(context.resources).language
        view.setLanguage(getLanguageFromPrefs())
    }

    private fun getLanguageFromPrefs() = when (preferences.language) {
        LocaleManager.RUSSIAN -> resourceProvider.getString(R.string.russian)
        else -> resourceProvider.getString(R.string.english)
    }

    private fun setRxEventsListeners() {
        disposable.add(RxBus.listen(LanguageChanged::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onLanguageChanged(it.language) })
    }

    private fun onLanguageChanged(language: String?) {
        if (language != null) preferences.language = language
        LocaleManager.setNewLocale(context, preferences.language)
        System.exit(0)
    }

    override fun onLanguageClicked() {
        view.showLanguageDialog(preferences.language)
    }

    override fun onStop() {
        disposable.clear()
    }
}