package com.rmnivnv.cryptomoon.view.coins

import android.content.Intent
import com.rmnivnv.cryptomoon.model.DisplayCoin

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface ICoins {

    interface View {
        fun updateRecyclerView()
        fun hideRefreshing()
        fun enableSwipeToRefresh()
        fun disableSwipeToRefresh()
        fun startActivityByIntent(intent: Intent)
    }

    interface Presenter {
        fun onCreate(component: CoinsComponent, coins: ArrayList<DisplayCoin>)
        fun onViewCreated()
        fun onStart()
        fun onStop()
        fun onDestroy()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: DisplayCoin)
    }
}