package com.rmnivnv.cryptomoon.ui.mycoins

import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.db.CMDatabase

class MyCoinsDatabaseRepository(
    private val db: CMDatabase
) : MyCoinsContract.DatabaseRepository {

    override suspend fun updateMyCoins(coins: List<CoinEntity>) = with(db.myCoinsDao()) {
        clear()
        insertList(coins)
    }
}