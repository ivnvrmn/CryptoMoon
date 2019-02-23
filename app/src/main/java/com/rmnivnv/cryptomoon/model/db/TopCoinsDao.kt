package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 02/09/2017.
 */
@Dao
interface TopCoinsDao {

    @Query("SELECT * FROM top_coins")
    fun getAll(): Flowable<List<TopCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TopCoinEntity>)

    @Query("DELETE FROM top_coins")
    fun clear()
}