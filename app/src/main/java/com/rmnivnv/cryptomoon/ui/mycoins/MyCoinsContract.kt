package com.rmnivnv.cryptomoon.ui.mycoins

import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.PricesResponse
import io.reactivex.disposables.Disposable

/**
 * Created by rmnivnv on 11/07/2017.
 */
interface MyCoinsContract {

    interface View {
        fun updateMyCoins(myCoins: List<CoinEntity>)
        fun hideRefreshing()
        fun showSwipeToRefresh()
        fun hideSwipeToRefresh()
        fun showCoinInfoActivity(name: String)
        fun showEmptyText()
        fun hideEmptyText()
    }

    interface Presenter {
        fun onStart()
        fun onStop()
        fun onSwipeUpdate()
        fun onCoinClicked(coin: CoinEntity)
    }

    interface Observables {
        fun observeMyCoins(callback: (List<CoinEntity>) -> Unit): Disposable
        fun observePageChanging(callback: (Int) -> Unit): Disposable
        fun observeDeleteCoinsEvent(callback: () -> Unit): Disposable
        fun observeSortMethodEvent(callback: (String?) -> Unit): Disposable
        fun observeHoldingsUpdates(callback: (List<HoldingData>) -> Unit): Disposable
    }

    interface ApiRepository {
        suspend fun getPrices(coins: String): Result<PricesResponse>
    }

    interface DatabaseRepository {
        suspend fun updateMyCoins(coins: List<CoinEntity>)
    }
}