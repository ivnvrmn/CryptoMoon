package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rmnivnv.cryptomoon.model.*



/**
 * Created by rmnivnv on 22/07/2017.
 */
@Database(entities = [
        Coin::class,
        InfoCoin::class,
        TopCoinData::class,
        HoldingData::class], version = 2)
abstract class CMDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDao

    abstract fun allCoinsDao(): AllCoinsDao

    abstract fun topCoinsDao(): TopCoinsDao

    abstract fun holdingsDao(): HoldingsDao
}