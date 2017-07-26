package com.rmnivnv.cryptomoon.view.coins.addCoin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.rmnivnv.cryptomoon.R

class AddCoinActivity : AppCompatActivity(), IAddCoin.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_coin)
    }
}
