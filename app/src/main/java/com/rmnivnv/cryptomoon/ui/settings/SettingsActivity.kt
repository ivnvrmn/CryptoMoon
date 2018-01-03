package com.rmnivnv.cryptomoon.ui.settings

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.base.BaseActivity
import com.rmnivnv.cryptomoon.ui.settings.dialogs.LanguageDialog
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity(), Settings.View {

    @Inject lateinit var presenter: Settings.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupToolbar()
        presenter.onCreate()
        language_layout.setOnClickListener { presenter.onLanguageClicked() }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setLanguage(language: String) {
        settings_language.text = language
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun showLanguageDialog() {
        val dialog = LanguageDialog()
        dialog.show(supportFragmentManager, "languageDialog")
    }
}
