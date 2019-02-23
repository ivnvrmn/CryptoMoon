package com.rmnivnv.cryptomoon.ui.topcoins

import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import com.rmnivnv.cryptomoon.model.rxbus.MainCoinsListUpdatedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TopCoinsObservables(
    private val db: CMDatabase,
    private val pageController: PageController
) : TopCoinsContract.Observables {

    override fun observeTopCoins(callback: (List<TopCoinEntity>) -> Unit): Disposable {
        return db.topCoinsDao().getAll()
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

    override fun observeMainCoinsUpdating(callback: () -> Unit): Disposable {
        return RxBus.listen(MainCoinsListUpdatedEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { callback() }
    }
}