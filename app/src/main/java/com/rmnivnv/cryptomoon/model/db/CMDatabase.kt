package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rmnivnv.cryptomoon.model.Coin
import com.rmnivnv.cryptomoon.model.CoinBodyDisplay

/**
 * Created by rmnivnv on 22/07/2017.
 */
@Database(entities = arrayOf(CoinBodyDisplay::class, Coin::class), version = 1)
abstract class CMDatabase : RoomDatabase() {

    abstract fun displayCoinsDao(): DisplayCoinsDao

    abstract fun allCoinsDao(): AllCoinsDao

}