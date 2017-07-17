package com.rmnivnv.cryptomoon.view.fragments.coins

import com.rmnivnv.cryptomoon.model.CoinBodyDisplay

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface ICoins {

    interface View {
        fun updateRecyclerView()
    }

    interface Presenter {
        fun onCreate(component: CoinsComponent)
        fun onViewCreated(coins: ArrayList<CoinBodyDisplay>)
        fun onDestroy()
    }
}