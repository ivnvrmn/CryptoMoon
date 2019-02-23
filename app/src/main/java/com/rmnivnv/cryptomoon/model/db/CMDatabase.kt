package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity

/**
 * Created by rmnivnv on 22/07/2017.
 */
@Database(entities = [
    Coin::class,
    InfoCoin::class,
    HoldingData::class,
    TopCoinEntity::class,
    CoinEntity::class
], version = 3)
abstract class CMDatabase : RoomDatabase() {

    abstract fun myCoinsDao(): MyCoinsDao

    abstract fun allCoinsDao(): AllCoinsDao

    abstract fun topCoinsDao(): TopCoinsDao

    abstract fun holdingsDao(): HoldingsDao
}