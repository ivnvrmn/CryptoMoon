package com.rmnivnv.cryptomoon.ui.holdings

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.Toaster
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ivanov_r on 13.09.2017.
 */
class HoldingsPresenter @Inject constructor(private val view: IHoldings.View,
                                            private val db: CMDatabase,
                                            private val resourceProvider: ResourceProvider,
                                            private val toaster: Toaster) : IHoldings.Presenter {

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

    override fun onStop() {
        disposable.clear()
    }

    override fun onItemSwiped(position: Int?) {
        if (position != null) {
            disposable.add(Single.fromCallable { db.holdingsDao().deleteHolding(holdings[position]) }
                    .subscribeOn(Schedulers.io())
                    .subscribe())
            toaster.toastShort(resourceProvider.getString(R.string.holdings_deleted))
        }
    }
}