package com.rmnivnv.cryptomoon.ui.topcoins

import android.view.View
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.rxbus.MainCoinsListUpdatedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.top_coin_item.view.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by rmnivnv on 02/09/2017.
 */
class TopCoinsPresenter @Inject constructor(
    private val view: TopCoinsContract.View,
    private val db: CMDatabase,
    private val networkRequests: NetworkRequests,
    private val repository: TopCoinsRepository,
    private val coinsController: CoinsController,
    private val resProvider: ResourceProvider,
    private val pageController: PageController,
    private val toaster: Toaster,
    private val logger: Logger
) : TopCoinsContract.Presenter {

    private val disposable = CompositeDisposable()
    private var coins: ArrayList<TopCoinData> = ArrayList()
    private var isRefreshing = false
    private var needToUpdate = false

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    override fun onStart() {
        subscribeToObservables()
        updateTopCoins()
        updateAllCoins()
    }

    private fun subscribeToObservables() {
        disposable.apply {
            add(db.topCoinsDao().getAllTopCoins()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { onTopCoinsUpdated(it) })
            add(db.allCoinsDao().getAllCoins()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { onAllCoinsUpdated(it) })
            add(RxBus.listen(MainCoinsListUpdatedEvent::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { onMainCoinsUpdated() })
            add(pageController.getPageObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { onPageChanged(it) })
        }
    }

    private fun onTopCoinsUpdated(list: List<TopCoinData>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            coins.sortBy { it.rank }
            view.updateList(coins)
        }
    }

    private fun onAllCoinsUpdated(list: List<InfoCoin>) {
        if (list.isNotEmpty()) {
            updateTopCoins()
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

    override fun onStop() {
        disposable.clear()
        coroutineContext.cancel()
    }

    private fun updateTopCoins() {
        scope.launch {
            repository.getTopCoins()?.also { onTopCoinsReceived(it) }
        }
    }

    private fun onTopCoinsReceived(coins: List<TopCoinData>) {
        if (coins.isNotEmpty()) {
            coinsController.saveTopCoinsList(coins)
            if (isRefreshing) {
                view.hideRefreshing()
                isRefreshing = false
            }
        }
    }

    private fun updateAllCoins() {
        if (coinsController.allInfoCoinsIsEmpty()) {
            disposable.add(networkRequests.getAllCoins()
                    .subscribe({ onAllCoinsReceived(it) },
                            { logger.logError("getAllCoinsInfo $it") }))
        } else {
            updateTopCoins()
        }
    }

    private fun onAllCoinsReceived(list: ArrayList<InfoCoin>) {
        if (list.isNotEmpty()) {
            coinsController.saveAllCoinsInfo(list)
        }
    }

    override fun onCoinClicked(coin: TopCoinData) {
        view.startCoinInfoActivity(coin.symbol)
    }

    override fun onSwiped() {
        isRefreshing = true
        updateTopCoins()
    }

    //todo get rid of View
    override fun onAddCoinClicked(coin: TopCoinData, itemView: View) {
        itemView.top_coin_add_loading.visibility = View.VISIBLE
        itemView.top_coin_add_icon.visibility = View.GONE
        val coinFrom = Coin(from = coin.symbol!!, to = USD)
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coinFrom)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinAdded(it, itemView) }, { onError(itemView) }))
    }

    private fun onCoinAdded(list: ArrayList<Coin>, itemView: View) {
        if (list.isNotEmpty()) {
            coinsController.saveCoinsList(list)
            itemView.top_coin_add_icon.setImageDrawable(resProvider.getDrawable(R.drawable.ic_done))
            toaster.toastShort(resProvider.getString(R.string.coin_added))
        } else {
            toaster.toastShort(resProvider.getString(R.string.error))
        }
        afterAdded(itemView)
    }

    private fun onError(itemView: View) {
        afterAdded(itemView)
        toaster.toastShort(resProvider.getString(R.string.error))
    }

    private fun afterAdded(itemView: View) {
        itemView.top_coin_add_loading.visibility = View.GONE
        itemView.top_coin_add_icon.visibility = View.VISIBLE
    }
}