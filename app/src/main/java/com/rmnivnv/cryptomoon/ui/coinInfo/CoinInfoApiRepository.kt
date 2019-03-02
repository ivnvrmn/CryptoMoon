package com.rmnivnv.cryptomoon.ui.coinInfo

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.USD
import com.rmnivnv.cryptomoon.model.data.HistoryPeriod
import com.rmnivnv.cryptomoon.model.network.BaseRepository
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.api.CryptoCompareAPI
import com.rmnivnv.cryptomoon.model.network.data.HistoricalDataResponse
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import java.io.IOException

private const val HISTORY_MINUTE = "histominute"
private const val HISTORY_HOUR = "histohour"
private const val HISTORY_DAY = "histoday"
private const val DEFAULT_LIMIT = 30
private const val DEFAULT_AGGREGATE = 1

class CoinInfoApiRepository(
    private val cryptoCompareAPI: CryptoCompareAPI,
    private val resourceProvider: ResourceProvider
) : BaseRepository(), CoinInfoContract.ApiRepository {

    override suspend fun requestHistory(period: HistoryPeriod, coinFrom: String) = safeApiCall(
        { history(period, coinFrom) },
        resourceProvider.getString(R.string.error)
    )

    private suspend fun history(
        period: HistoryPeriod,
        coinFrom: String
    ): Result<HistoricalDataResponse> {
        val historyPeriod: String
        var limit = DEFAULT_LIMIT
        var aggregate = DEFAULT_AGGREGATE

        when (period) {
            HistoryPeriod.HOUR -> {
                historyPeriod = HISTORY_MINUTE
                limit = 60
                aggregate = 2
            }
            HistoryPeriod.HOURS_12 -> {
                historyPeriod = HISTORY_HOUR
                limit = 12
            }
            HistoryPeriod.HOURS_24 -> {
                historyPeriod = HISTORY_HOUR
                limit = 24
            }
            HistoryPeriod.DAYS_3 -> {
                historyPeriod = HISTORY_HOUR
                aggregate = 2
            }
            HistoryPeriod.WEEK -> {
                historyPeriod = HISTORY_HOUR
                aggregate = 6
            }
            HistoryPeriod.MONTH -> {
                historyPeriod = HISTORY_DAY
            }
            HistoryPeriod.MONTHS_3 -> {
                historyPeriod = HISTORY_DAY
                aggregate = 3
            }
            HistoryPeriod.MONTHS_6 -> {
                historyPeriod = HISTORY_DAY
                aggregate = 6
            }
            HistoryPeriod.YEAR -> {
                historyPeriod = HISTORY_DAY
                aggregate = 12
            }
        }

        val response = cryptoCompareAPI.getHistoricalData(
            historyPeriod,
            coinFrom,
            USD,
            limit,
            aggregate
        ).await()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(IOException(resourceProvider.getString(R.string.error)))
        } else {
            Result.Error(IOException(resourceProvider.getString(R.string.error)))
        }
    }
}