package com.rmnivnv.cryptomoon.view.coins.addCoin

import com.rmnivnv.cryptomoon.model.Coin
import com.rmnivnv.cryptomoon.model.db.CMDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 27/07/2017.
 */
class AddCoinPresenter : IAddCoin.Presenter {

    @Inject lateinit var view: IAddCoin.View
    @Inject lateinit var db: CMDatabase

    private val disposable = CompositeDisposable()
    private var allCoins: List<Coin>? = null
    private lateinit var matches: ArrayList<Coin>

    override fun onCreate(component: AddCoinComponent, matches: ArrayList<Coin>) {
        component.inject(this)
        this.matches = matches
        checkAllCoins()
    }

    private fun checkAllCoins() {
        disposable.add(db.allCoinsDao().getAllCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        println("checkAllCoins COINS SIZE = " + it.size)
                        allCoins = it
                    }
                })
        )
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onFromTextChanged(text: String) {
        if (text.isNotEmpty()) {
            val matchesList = allCoins?.filter {
                (it.coinName?.contains(text, true) ?: false) || (it.name?.contains(text, true) ?: false)
            }?.reversed()
            if (matchesList != null && matchesList.isNotEmpty()) {
                view.setMatchesResultSize(matchesList.size.toString())
                matches.clear()
                matches.addAll(matchesList)
                view.updateRecyclerView()
            } else {
                view.setMatchesResultSize("0")
            }
        } else {
            matches.clear()
            view.updateRecyclerView()
            view.setMatchesResultSize("0")
        }
    }

    override fun onToTextChanged(text: String) {

    }

    override fun onFromItemClicked(coin: Coin) {
        println("COIN CLICKED " + coin.name)
    }
}