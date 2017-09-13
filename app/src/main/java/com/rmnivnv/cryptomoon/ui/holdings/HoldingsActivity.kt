package com.rmnivnv.cryptomoon.ui.holdings

import android.os.Bundle

import com.rmnivnv.cryptomoon.R
import dagger.android.support.DaggerAppCompatActivity

class HoldingsActivity : DaggerAppCompatActivity(), IHoldings.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holdings)
    }
}
