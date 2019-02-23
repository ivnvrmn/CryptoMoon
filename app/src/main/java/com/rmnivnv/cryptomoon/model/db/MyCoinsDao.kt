package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.*
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 24/12/2017.
 */

@Dao
interface MyCoinsDao {

    @Query("SELECT * FROM my_coins")
    fun getAllCoins(): Flowable<List<CoinEntity>>

    @Query("SELECT * FROM my_coins WHERE rawfrom_symbol LIKE :from AND rawto_symbol LIKE :to LIMIT 1")
    fun getCoin(from: String, to: String): CoinEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: CoinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<CoinEntity>)

    @Delete
    fun deleteCoin(coin: CoinEntity)

    @Delete
    fun deleteCoins(coins: List<CoinEntity>)

    @Query("DELETE FROM my_coins")
    fun clear()
}