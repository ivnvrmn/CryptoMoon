package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity

class TopCoinsDatabaseRepository(
    private val db: CMDatabase
) : TopCoinsContract.DatabaseRepository {

    override suspend fun updateTopCoins(coins: List<TopCoinEntity>) = with(db.topCoinsDao()) {
        clear()
        insert(coins)
    }

    override suspend fun saveCoin(coin: CoinEntity) {
        db.myCoinsDao().insert(coin)
    }

    override suspend fun coinIsAdded(coin: TopCoinEntity): Boolean {
        return db.myCoinsDao().getCoin(coin.raw.usd.fromSymbol, coin.raw.usd.toSymbol) != null
    }
}