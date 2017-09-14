package com.rmnivnv.cryptomoon.ui.holdings

import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 13.09.2017.
 */
class HoldingsPresenter @Inject constructor(private val view: IHoldings.View,
                                            private val db: CMDatabase) : IHoldings.Presenter {

    private val disposable = CompositeDisposable()
    private var holdings: ArrayList<HoldingData> = ArrayList()

    override fun onCreate(holdings: ArrayList<HoldingData>) {
        this.holdings = holdings
        addHoldingsChangesObservable()
    }

    private fun addHoldingsChangesObservable() {
        disposable.add(db.holdingsDao().getAllHoldings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onHoldingsUpdate(it) }))
    }

    private fun onHoldingsUpdate(list: List<HoldingData>) {
        if (list.isNotEmpty()) {
            holdings.clear()
            holdings.addAll(list)
            view.updateRecyclerView()
        }
    }

    override fun onDestroy() {
        disposable.clear()
    }
}