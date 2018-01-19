package com.rmnivnv.cryptomoon.ui.main

import com.rmnivnv.cryptomoon.model.COINS_FRAGMENT_PAGE_POSITION
import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.OnDeleteCoinsMenuItemClickedEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.MultiSelector
import com.rmnivnv.cryptomoon.model.PageController
import com.rmnivnv.cryptomoon.model.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by rmnivnv on 06/07/2017.
 */
class MainPresenter @Inject constructor(private val view: IMain.View,
                                        private val multiSelector: MultiSelector,
                                        private val pageController: PageController,
                                        private val preferences: Preferences) : IMain.Presenter {

    private val disposable = CompositeDisposable()

    override fun onStart() {
        setObservers()
    }

    private fun setObservers() {
        disposable.add(RxBus.listen(CoinsLoadingEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setCoinsLoadingVisibility(it.isLoading)
                })
        disposable.add(multiSelector.getSelectorObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.setMenuIconsVisibility(it)
                })
        disposable.add(pageController.getPageObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onPageChanged(it) })
    }

    private fun onPageChanged(position: Int) {
        view.setSortVisible(position == COINS_FRAGMENT_PAGE_POSITION)
    }

    override fun onStop() {
        disposable.clear()
    }

    override fun onAddCoinClicked() {
        view.startAddCoinActivity()
    }

    override fun onSortClicked() {
        view.showCoinsSortDialog(preferences.sortBy)
    }

    override fun onSettingsClicked() {
        view.openSettings()
    }

    override fun onDeleteClicked() {
        view.setMenuIconsVisibility(false)
        RxBus.publish(OnDeleteCoinsMenuItemClickedEvent())
    }

    override fun onPageSelected(position: Int) {
        pageController.pageSelected(position)
    }
}