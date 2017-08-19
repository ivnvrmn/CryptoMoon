package com.rmnivnv.cryptomoon.view.coins.coinInfo

import android.os.Bundle

/**
 * Created by ivanov_r on 17.08.2017.
 */
interface ICoinInfo {
    interface View {
        fun setTitle(title: String)
        fun setLogo(url: String)
        fun setMainPrice(price: String)
    }

    interface Presenter {
        fun onCreate(component: CoinInfoComponent, extras: Bundle)
        fun onDestroy()
    }
}