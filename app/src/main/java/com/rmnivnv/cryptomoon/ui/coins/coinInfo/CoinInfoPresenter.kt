package com.rmnivnv.cryptomoon.ui.coins.coinInfo

import android.content.Context
import android.os.Bundle
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.network.NetworkRequests
import com.rmnivnv.cryptomoon.utils.createCoinsMapWithCurrencies
import com.rmnivnv.cryptomoon.utils.logDebug
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 17.08.2017.
 */
class CoinInfoPresenter @Inject constructor(private val context: Context,
                                            private val view: ICoinInfo.View,
                                            private val coinsController: CoinsController,
                                            private val networkRequests: NetworkRequests,
                                            private val graphMaker: GraphMaker) : ICoinInfo.Presenter {

    private val disposable = CompositeDisposable()
    private var coin: DisplayCoin? = null
    private var from: String? = null
    private var to: String? = null

    override fun onCreate(extras: Bundle) {
        if (extras[NAME] != null && extras[TO] != null) {
            from = extras.getString(NAME)
            to = extras.getString(TO)
            getCoinByName(from, to)
        }
    }

    private fun getCoinByName(from: String?, to: String?) {
        disposable.add(Single.fromCallable { coinsController.getDisplayCoin(from!!, to!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCoinArrived(it) }, { onFindCoinError(it) }))
    }

    private fun onCoinArrived(coin: DisplayCoin) {
        this.coin = coin
        view.setTitle(coin.fullName)
        view.setLogo(coin.imgUrl)
        view.setMainPrice(coin.PRICE)
        requestHisto(MONTH)
        setCoinInfo()
    }

    private fun requestHisto(period: String) {
        disposable.add(networkRequests.getHistoPeriod(period, coin!!.from, coin!!.to,
                object : GetHistoCallback {
                    override fun onSuccess(histoList: ArrayList<HistoData>) {
                        println("histo size = " + histoList.size)
                        view.disableGraphLoading()
                        view.drawChart(graphMaker.makeChart(histoList, period))
                    }

                    override fun onError(t: Throwable) {
                        view.disableGraphLoading()
                    }
                }))
    }

    private fun setCoinInfo() {
        view.setOpen(coin!!.OPEN24HOUR)
        view.setHigh(coin!!.HIGH24HOUR)
        view.setLow(coin!!.LOW24HOUR)
        view.setChange(coin!!.CHANGE24HOUR)
        view.setChangePct(coin!!.CHANGEPCT24HOUR)
        view.setSupply(coin!!.SUPPLY)
        view.setMarketCap(coin!!.MKTCAP)
    }

    private fun onFindCoinError(throwable: Throwable) {
        context.logDebug("getCoinByName error " + throwable.toString())
        requestCoinInfo(DisplayCoin(from = this.from!!, to = this.to!!))
    }

    private fun requestCoinInfo(coin: DisplayCoin) {
        disposable.add(networkRequests.getPrice(createCoinsMapWithCurrencies(listOf(coin)),
                object : GetPriceCallback { override fun onSuccess(coinsInfoList: ArrayList<DisplayCoin>?) {
                    if (coinsInfoList != null && coinsInfoList.isNotEmpty()) {
                        val arrivedCoin = coinsInfoList[0]
                        coinsController.addAdditionalInfo(arrivedCoin)
                        onCoinArrived(arrivedCoin)
                    }
                }

                    override fun onError(t: Throwable) {
                    }
                }))
    }

    override fun onSpinnerItemClicked(selectedItem: String) {
        if (coin != null) {
            view.enableGraphLoading()
            requestHisto(selectedItem)
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }
}