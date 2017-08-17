package com.rmnivnv.cryptomoon.view.coins.coinInfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.rmnivnv.cryptomoon.R

class CoinInfoActivity : AppCompatActivity(), ICoinInfo.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_info)
    }
}
