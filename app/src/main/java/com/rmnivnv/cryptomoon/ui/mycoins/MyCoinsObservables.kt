package com.rmnivnv.cryptomoon.ui.mycoins

import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.rxbus.CoinsSortMethodUpdated
import com.rmnivnv.cryptomoon.model.rxbus.OnDeleteCoinsMenuItemClickedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyCoinsObservables(
    private val db: CMDatabase,
    private val pageController: PageController
) : MyCoinsContract.Observables {

    override fun observeMyCoins(callback: (List<CoinEntity>) -> Unit): Disposable {
        return db.myCoinsDao().getAllCoins()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback(it) }
    }

    override fun observePageChanging(callback: (Int) -> Unit): Disposable {
        return pageController.getPageObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback(it) }
    }

    override fun observeDeleteCoinsEvent(callback: () -> Unit): Disposable {
        return RxBus.listen(OnDeleteCoinsMenuItemClickedEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback() }
    }

    override fun observeSortMethodEvent(callback: (String?) -> Unit): Disposable {
        return RxBus.listen(CoinsSortMethodUpdated::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback(it.sort) }
    }

    override fun observeHoldingsUpdates(callback: (List<HoldingData>) -> Unit): Disposable {
        return db.holdingsDao().getAllHoldings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback(it) }
    }
}