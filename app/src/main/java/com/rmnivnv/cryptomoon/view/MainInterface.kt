package com.rmnivnv.cryptomoon.view

/**
 * Created by rmnivnv on 06/07/2017.
 */
interface MainInterface {

    interface View {
        fun setCoinsLoadingVisibility(isLoading: Boolean)
    }

    interface Presenter {
        fun onCreate(component: MainComponent)

    }
}