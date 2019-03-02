package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.TopCoinsResponse
import io.reactivex.disposables.Disposable

/**
 * Created by rmnivnv on 02/09/2017.
 */
interface TopCoinsContract {

    interface View {
        fun updateList(coins: List<TopCoinEntity>)
        fun updateItem(position: Int)
        fun hideRefreshing()
        fun startCoinInfoActivity(coin: CoinEntity)
    }

    interface Presenter {
        fun onViewCreated()
        fun onDestroy()
        fun onCoinClicked(coin: TopCoinEntity)
        fun onAddCoinClicked(coin: TopCoinEntity)
        fun onSwiped()
        fun checkCoinIsAdded(coin: TopCoinEntity, result: (Boolean) -> Unit)
    }

    interface Observables {
        fun observeTopCoins(callback: (List<TopCoinEntity>) -> Unit): Disposable
        fun observePageChanging(callback: (Int) -> Unit): Disposable
        fun observeMainCoinsUpdating(callback: () -> Unit): Disposable
    }

    interface DatabaseRepository {
        suspend fun updateTopCoins(coins: List<TopCoinEntity>)
        suspend fun saveCoin(coin: CoinEntity)
        suspend fun coinIsAdded(coin: TopCoinEntity): Boolean
    }

    interface ApiRepository {
        suspend fun getTopCoins(): Result<TopCoinsResponse>
    }

    interface ResourceProvider {
        fun getCoinAddedText(): String
    }
}