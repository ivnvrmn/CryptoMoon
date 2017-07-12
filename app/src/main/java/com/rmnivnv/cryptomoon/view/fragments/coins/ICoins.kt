package com.rmnivnv.cryptomoon.view.fragments.coins

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface ICoins {

    interface View {

    }

    interface Presenter {
        fun onCreate(component: CoinsComponent)
        fun onViewCreated()
        fun onDestroy()
    }
}