package com.rmnivnv.cryptomoon.ui.topCoins

import android.content.Context
import android.content.Intent
import android.view.View
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.rxbus.MainCoinsListUpdatedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.createCoinsMapWithCurrencies
import com.rmnivnv.cryptomoon.ui.coinInfo.CoinInfoActivity
import com.rmnivnv.cryptomoon.utils.toastShort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.top_coin_item.view.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 02/09/2017.
 */
class TopCoinsPresenter @Inject constructor(private val context: Context,
                                            private val view: ITopCoins.View,
                                            private val db: CMDatabase,
                                            private val networkRequests: NetworkRequests,
                                            private val coinsController: CoinsController,
                                            private val resProvider: ResourceProvider,
                                            private val pageController: PageController) : ITopCoins.Presenter {
    private val disposable = CompositeDisposable()
    private var coins: ArrayList<TopCoinData> = ArrayList()
    private var isRefreshing = false
    private var needToUpdate = false

    override fun onCreate(coins: ArrayList<TopCoinData>) {
        this.coins = coins
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        disposable.add(db.topCoinsDao().getAllTopCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinsUpdated(it) }))
        disposable.add(RxBus.listen(MainCoinsListUpdatedEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onMainCoinsUpdated() })
        addOnPageChangedObservable()
    }

    private fun onCoinsUpdated(list: List<TopCoinData>) {
        if (list.isNotEmpty()) {
            coins.clear()
            coins.addAll(list)
            coins.sortBy { it.rank }
            view.updateRecyclerView()
        }
    }

    private fun onMainCoinsUpdated() {
        needToUpdate = true
    }

    private fun addOnPageChangedObservable() {
        disposable.add(pageController.getPageObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onPageChanged(it) })
    }

    private fun onPageChanged(position: Int) {
        if (position == TOP_COINS_FRAGMENT_PAGE_POSITION && needToUpdate) {
            view.updateRecyclerView()
            needToUpdate = false
        }
    }

    override fun onStart() {
        updateTopCoins()
    }

    override fun onDestroy() {
        disposable.clear()
    }

    private fun updateTopCoins() {
        disposable.add(networkRequests.getTopCoins(object : GetTopCoinsCallback {
            override fun onSuccess(coins: List<TopCoinData>) {
                if (coins.isNotEmpty()) {
                    coinsController.saveTopCoinsList(coins)
                    if (isRefreshing) {
                        view.hideRefreshing()
                        isRefreshing = false
                    }
                }
            }

            override fun onError(t: Throwable) {

            }
        }))
    }

    override fun onCoinClicked(coin: TopCoinData) {
        val intent = Intent(context, CoinInfoActivity::class.java)
        intent.putExtra(NAME, coin.symbol)
        intent.putExtra(TO, USD)
        view.startActivityByIntent(intent)
    }

    override fun onSwipeUpdate() {
        isRefreshing = true
        updateTopCoins()
    }

    override fun onAddCoinClicked(coin: TopCoinData, itemView: View) {
        itemView.top_coin_add_loading.visibility = View.VISIBLE
        itemView.top_coin_add_icon.visibility = View.GONE
        val displayCoin = DisplayCoin(from = coin.symbol!!, to = USD)
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(displayCoin)),
                object : GetPriceCallback {
                    override fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?) {
                        if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                            coinsController.saveDisplayCoinList(coinsInfoList)
                            itemView.top_coin_add_icon.setImageDrawable(resProvider.getDrawable(R.drawable.ic_done))
                            context.toastShort(resProvider.getString(R.string.coin_added))
                        } else {
                            context.toastShort(resProvider.getString(R.string.error))
                        }
                        afterAdded(itemView)
                    }

                    override fun onError(t: Throwable) {
                        afterAdded(itemView)
                        context.toastShort(resProvider.getString(R.string.error))
                    }
                }))
    }

    private fun afterAdded(itemView: View) {
        itemView.top_coin_add_loading.visibility = View.GONE
        itemView.top_coin_add_icon.visibility = View.VISIBLE
    }
}