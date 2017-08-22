package com.rmnivnv.cryptomoon.view.coins.coinInfo

import android.os.Bundle
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.network.NetworkRequests
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 17.08.2017.
 */
class CoinInfoPresenter : ICoinInfo.Presenter {

    @Inject lateinit var view: ICoinInfo.View
    @Inject lateinit var db: CMDatabase
    @Inject lateinit var coinsController: CoinsController
    @Inject lateinit var networkRequests: NetworkRequests
    @Inject lateinit var graphMaker: GraphMaker

    private val disposable = CompositeDisposable()
    private lateinit var coin: DisplayCoin

    override fun onCreate(component: CoinInfoComponent, extras: Bundle) {
        component.inject(this)
        getCoinByName(extras)
    }

    private fun getCoinByName(extras: Bundle) {
        if (extras[NAME] != null) {
            disposable.add(Single.fromCallable { coinsController.getDisplayCoin(extras.getString(NAME)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onCoinArrived(it) }, {  }))
        }
    }

    private fun onCoinArrived(coin: DisplayCoin) {
        this.coin = coin
        view.setTitle(coin.fullName)
        view.setLogo(coin.imgUrl)
        view.setMainPrice(coin.PRICE)
        requestHisto(MONTH)
    }

    private fun requestHisto(period: String) {
        disposable.add(networkRequests.getHistoPeriod(period, coin.from, USD,
                object : GetHistoCallback {
                    override fun onSuccess(histoList: ArrayList<HistoData>) {
                        println("histo size = " + histoList.size)
                        view.drawChart(graphMaker.makeChart(histoList))
                    }

                    override fun onError(t: Throwable) {

                    }
                }))
    }

    private fun drawGraph() {

    }

    override fun onSpinnerItemClicked(selectedItem: String) {
        requestHisto(selectedItem)
    }

    override fun onDestroy() {
        disposable.clear()
    }
}