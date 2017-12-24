package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.*
import com.rmnivnv.cryptomoon.model.Coin
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 24/12/2017.
 */

@Dao
interface CoinsDao {

    @Query("SELECT * FROM coins")
    fun getAllCoins(): Flowable<List<Coin>>

    @Query("SELECT * FROM coins WHERE from_name LIKE :from AND to_name LIKE :to LIMIT 1")
    fun getCoin(from: String, to: String): Coin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<Coin>)

    @Delete
    fun deleteCoin(coin: Coin)

    @Delete
    fun deleteCoins(coins: List<Coin>)
}