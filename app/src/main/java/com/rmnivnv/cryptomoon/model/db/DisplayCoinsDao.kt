package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.*
import com.rmnivnv.cryptomoon.model.DisplayCoin
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 22/07/2017.
 */

@Dao
interface DisplayCoinsDao {

    @Query("SELECT * FROM display_coins")
    fun getAllCoins(): Flowable<List<DisplayCoin>>

    @Query("SELECT * FROM display_coins WHERE from_name LIKE :from AND to_name LIKE :currency LIMIT 1")
    fun getDisplayCoin(from: String, currency: String): DisplayCoin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: DisplayCoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<DisplayCoin>)

    @Delete
    fun deleteCoin(coin: DisplayCoin)

    @Delete
    fun deleteCoins(coins: List<DisplayCoin>)
}
