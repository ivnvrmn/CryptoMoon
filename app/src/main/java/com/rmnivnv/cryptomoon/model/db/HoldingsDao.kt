package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.*
import com.rmnivnv.cryptomoon.model.HoldingData
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 09/09/2017.
 */
@Dao
interface HoldingsDao {

    @Query("SELECT * FROM holdings")
    fun getAllHoldings(): Flowable<List<HoldingData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(holdingData: HoldingData)

    @Delete
    fun deleteHolding(holdingData: HoldingData)
}