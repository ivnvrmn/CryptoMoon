package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rmnivnv.cryptomoon.model.Coin
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 28/07/2017.
 */
@Dao
interface AllCoinsDao {

    @Query("SELECT * FROM all_coins")
    fun getAllCoins(): Flowable<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<Coin>)

    @Query("DELETE FROM all_coins")
    fun clearAllCoins()
}