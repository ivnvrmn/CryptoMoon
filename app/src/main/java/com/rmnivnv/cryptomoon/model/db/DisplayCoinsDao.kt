package com.rmnivnv.cryptomoon.model.db

import android.arch.persistence.room.*
import com.rmnivnv.cryptomoon.model.Coin
import com.rmnivnv.cryptomoon.model.CoinBodyDisplay
import io.reactivex.Flowable

/**
 * Created by rmnivnv on 22/07/2017.
 */

@Dao
interface DisplayCoinsDao {

    @Query("SELECT * FROM coins_display")
    fun getAllCoins(): Flowable<List<CoinBodyDisplay>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: CoinBodyDisplay)
}
