package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rmnivnv.cryptomoon.model.InfoCoin
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.TopCoinData

/**
 * Created by rmnivnv on 22/07/2017.
 */
@Database(entities = arrayOf(DisplayCoin::class,
        InfoCoin::class,
        TopCoinData::class,
        HoldingData::class), version = 1)
abstract class CMDatabase : RoomDatabase() {

    abstract fun displayCoinsDao(): DisplayCoinsDao

    abstract fun allCoinsDao(): AllCoinsDao

    abstract fun topCoinsDao(): TopCoinsDao

    abstract fun holdingsDao(): HoldingsDao
}