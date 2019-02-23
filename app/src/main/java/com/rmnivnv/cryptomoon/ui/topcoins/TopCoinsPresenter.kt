package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import com.rmnivnv.cryptomoon.model.network.Result
import com.rmnivnv.cryptomoon.model.network.data.TopCoinsResponse
import com.rmnivnv.cryptomoon.utils.Logger
import com.rmnivnv.cryptomoon.utils.Toaster
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by rmnivnv on 02/09/2017.
 */

private const val TOP_COINS_FRAGMENT_PAGE_POSITION = 1

class TopCoinsPresenter @Inject constructor(
    private val view: TopCoinsContract.View,
    private val observables: TopCoinsContract.Observables,
    private val apiRepository: TopCoinsContract.ApiRepository,
    private val dbRepository: TopCoinsContract.DatabaseRepository,
    private val resProvider: TopCoinsContract.ResourceProvider,
    private val toaster: Toaster,
    private val logger: Logger
) : TopCoinsContract.Presenter {

    private val disposable = CompositeDisposable()
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private var coins: ArrayList<TopCoinEntity> = arrayListOf()
    private var isRefreshing = false
    private var needToUpdate = false

    override fun onViewCreated() {
        subscribeToObservables()
        updateTopCoins()
    }

    private fun subscribeToObservables() = with(disposable) {
        add(observables.observeTopCoins { onTopCoinsUpdated(it) })
        add(observables.observePageChanging { onPageChanged(it) })
        add(observables.observeMainCoinsUpdating { onMainCoinsUpdated() })
    }

    private fun onTopCoinsUpdated(list: List<TopCoinEntity>) {
        if (list.isNotEmpty()) {
            with(coins) {
                clear()
                addAll(list)
                view.updateList(this)
            }
        }
    }

    private fun onMainCoinsUpdated() {
        needToUpdate = true
    }

    private fun onPageChanged(position: Int) {
        if (position == TOP_COINS_FRAGMENT_PAGE_POSITION && needToUpdate) {
            view.updateList(coins)
            needToUpdate = false
        }
    }

    override fun onDestroy() {
        disposable.clear()
        coroutineContext.cancel()
    }

    private fun updateTopCoins() {
        scope.launch {
            val result = apiRepository.getTopCoins()
            when (result) {
                is Result.Success -> onTopCoinsReceived(result.data)
                is Result.Error -> logger.logError("updateTopCoins ${result.exception}")
            }
        }
    }

    private fun onTopCoinsReceived(response: TopCoinsResponse) {
        if (response.data.isNotEmpty()) {
            scope.launch { dbRepository.updateTopCoins(response.data) }
        }

        if (isRefreshing) {
            isRefreshing = false
            view.hideRefreshing()
        }
    }

    override fun onCoinClicked(coin: TopCoinEntity) {
        view.startCoinInfoActivity(coin.raw.usd.fromSymbol)
    }

    override fun onSwiped() {
        isRefreshing = true
        updateTopCoins()
    }

    override fun onAddCoinClicked(coin: TopCoinEntity) {
        scope.launch { dbRepository.saveCoin(CoinEntity(coin.raw, coin.display)) }
        toaster.toastShort(resProvider.getCoinAddedText())
        view.updateItem(getCoinPosition(coin.raw.usd.fromSymbol))
    }

    private fun getCoinPosition(symbol: String): Int =
        coins.indexOf(coins.find { symbol == it.raw.usd.fromSymbol })

    override fun checkCoinIsAdded(coin: TopCoinEntity, result: (Boolean) -> Unit) {
        scope.launch {
            dbRepository.coinIsAdded(coin).also {
                scope.launch(Dispatchers.Main) { result(it) }
            }
        }
    }
}