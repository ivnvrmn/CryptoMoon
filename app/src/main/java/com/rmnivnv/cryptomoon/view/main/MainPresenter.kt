package com.rmnivnv.cryptomoon.view.main

import android.content.Intent
import com.rmnivnv.cryptomoon.MainApp
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.CoinsSelectedEvent
import com.rmnivnv.cryptomoon.model.rxbus.OnDeleteCoinsMenuItemClickedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.view.coins.addCoin.AddCoinActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 06/07/2017.
 */
class MainPresenter : IMain.Presenter {

    @Inject lateinit var app: MainApp
    @Inject lateinit var view: IMain.View
    private val disposable = CompositeDisposable()

    override fun onCreate(component: MainComponent) {
        component.inject(this)
        setRxBusListen()
    }

    private fun setRxBusListen() {
        disposable.add(RxBus.listen(CoinsLoadingEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setCoinsLoadingVisibility(it.isLoading)
                })
        disposable.add(RxBus.listen(CoinsSelectedEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setDeleteIconVisibility(it.isSelected)
                })
    }

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onAddCoinClicked() {
        view.startActivityFromIntent(Intent(app, AddCoinActivity::class.java))
    }

    override fun onSettingsClicked() {
        //TODO implement on settings clicked
    }

    override fun onDeleteClicked() {
        view.setDeleteIconVisibility(false)
        RxBus.publish(OnDeleteCoinsMenuItemClickedEvent())
    }
}