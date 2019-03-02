package com.rmnivnv.cryptomoon.ui.mycoins

import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.PricesResponse
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.utils.Logger
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by rmnivnv on 11/07/2017.
 */
class MyCoinsPresenter @Inject constructor(
    private val view: MyCoinsContract.View,
    private val observables: MyCoinsContract.Observables,
    private val apiRepository: MyCoinsContract.ApiRepository,
    private val databaseRepository: MyCoinsContract.DatabaseRepository,
    private val logger: Logger
) : MyCoinsContract.Presenter {

    private val disposable = CompositeDisposable()
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private var coins: ArrayList<CoinEntity> = arrayListOf()
    private var isRefreshing = false
    private var isFirstStart = true

    override fun onStart() {
        subscribeToObservables()
        if (coins.isNotEmpty()) updatePrices()
    }

    private fun subscribeToObservables() = with(disposable) {
        add(observables.observeMyCoins { onMyCoinsUpdated(it) })
    }

    private fun onMyCoinsUpdated(myCoins: List<CoinEntity>) {
        if (myCoins.isNotEmpty()) {
            coins.clear()
            coins.addAll(myCoins)
            view.hideEmptyText()
            if (isFirstStart) {
                isFirstStart = false
                updatePrices()
            }
        } else {
            coins.clear()
            view.showEmptyText()
        }
        view.updateMyCoins(coins)
    }

    private fun updatePrices() {
        RxBus.publish(CoinsLoadingEvent(true))
        scope.launch {
            val result = apiRepository.getPrices(prepareCoinsQuery())
            when (result) {
                is Result.Success -> { onPricesUpdated(result.data) }
                is Result.Error -> {
                    logger.logError("updatePrices ${result.exception}")
                    afterRefreshing()
                }
            }
        }
    }

    private fun prepareCoinsQuery(): String {
        return StringBuilder().apply {
            coins.forEachIndexed { index, coin ->
                if (index == coins.lastIndex) {
                    append(coin.raw.usd.fromSymbol)
                } else {
                    append("${coin.raw.usd.fromSymbol},")
                }
            }
        }.toString()
    }

    private fun onPricesUpdated(response: PricesResponse) {
        val updatedCoins = getCoinsFromResponse(response)
        scope.launch { databaseRepository.updateMyCoins(updatedCoins) }
        afterRefreshing()
    }

    private fun getCoinsFromResponse(response: PricesResponse): List<CoinEntity> {
        return arrayListOf<CoinEntity>().apply {
            coins.forEach {
                val raw = response.raw[it.raw.usd.fromSymbol]
                val display = response.display[it.raw.usd.fromSymbol]
                if (raw != null && display != null) {
                    add(CoinEntity(raw, display))
                }
            }
        }
    }

    private fun afterRefreshing() {
        RxBus.publish(CoinsLoadingEvent(false))
        if (isRefreshing) {
            view.hideRefreshing()
            isRefreshing = false
            view.showSwipeToRefresh()
        }
    }

    override fun onStop() {
        disposable.clear()
        RxBus.publish(CoinsLoadingEvent(false))
    }

    override fun onSwipeUpdate() {
        isRefreshing = true
        updatePrices()
    }

    override fun onCoinClicked(coin: CoinEntity) {
        view.showCoinInfoActivity(coin  )
    }
}