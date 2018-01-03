package com.rmnivnv.cryptomoon.ui.main

/**
 * Created by rmnivnv on 06/07/2017.
 */
interface IMain {

    interface View {
        fun setCoinsLoadingVisibility(isLoading: Boolean)
        fun startAddCoinActivity()
        fun setMenuIconsVisibility(isSelected: Boolean)
        fun showToast(text: String)
        fun setSortVisible(isVisible: Boolean)
        fun showCoinsSortDialog()
        fun openSettings()
    }

    interface Presenter {
        fun onCreate()
        fun onAddCoinClicked()
        fun onSettingsClicked()
        fun onDeleteClicked()
        fun onPageSelected(position: Int)
        fun onSortClicked()
        fun onStop()
    }
}