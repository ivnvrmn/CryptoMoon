package com.rmnivnv.cryptomoon.view

import com.rmnivnv.cryptomoon.model.rxbus.CoinsLoadingEvent
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import javax.inject.Inject

/**
 * Created by rmnivnv on 06/07/2017.
 */
class MainPresenter : MainInterface.Presenter {

    @Inject lateinit var view: MainInterface.View

    override fun onCreate(component: MainComponent) {
        component.inject(this)
        setRxBusListen()
    }

    private fun setRxBusListen() {
        RxBus.listen(CoinsLoadingEvent::class.java).subscribe {
            view.setCoinsLoadingVisibility(it.isLoading)
        }
    }
}