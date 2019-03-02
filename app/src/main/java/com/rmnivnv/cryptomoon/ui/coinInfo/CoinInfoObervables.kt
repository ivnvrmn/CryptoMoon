package com.rmnivnv.cryptomoon.ui.coinInfo

import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.rxbus.TransactionAdded
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CoinInfoObervables : CoinInfoContract.Observables {

    override fun observeTransactions(callback: () -> Unit): Disposable {
        return RxBus.listen(TransactionAdded::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback() }
    }
}