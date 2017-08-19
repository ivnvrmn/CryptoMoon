package com.rmnivnv.cryptomoon.view.coins.coinInfo

import android.os.Bundle
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.NAME
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import io.reactivex.Observable
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

    private val disposable = CompositeDisposable()
    private lateinit var coin: DisplayCoin

    override fun onCreate(component: CoinInfoComponent, extras: Bundle) {
        component.inject(this)
        getCoinByName(extras)
    }

    private fun getCoinByName(extras: Bundle) {
        if (extras[NAME] != null) {
            disposable.add(Observable.fromCallable { coinsController.getDisplayCoin(extras.getString(NAME)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onCoinArrived(it) }))
        }
    }

    private fun onCoinArrived(coin: DisplayCoin) {
        this.coin = coin
        view.setTitle(coin.fullName)
        view.setLogo(coin.imgUrl)
        view.setMainPrice(coin.PRICE)
    }

    override fun onDestroy() {
        disposable.clear()
    }
}