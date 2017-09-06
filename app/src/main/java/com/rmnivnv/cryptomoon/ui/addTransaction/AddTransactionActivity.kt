package com.rmnivnv.cryptomoon.ui.addTransaction

import android.os.Bundle
import android.support.v7.widget.Toolbar

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AddTransactionActivity : DaggerAppCompatActivity(), IAddTransaction.View {

    @Inject lateinit var presenter: IAddTransaction.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.add_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }
}
