package com.rmnivnv.cryptomoon.view.coins.addCoin

import com.rmnivnv.cryptomoon.model.Coin

/**
 * Created by rmnivnv on 27/07/2017.
 */
interface IAddCoin {

    interface View {
        fun updateRecyclerView()
        fun setMatchesResultSize(matchesCount: String)
        fun disableMatchesCount()
        fun enableMatchesCount()
        fun enableLoadingLayout()
        fun disableLoadingLayout()
        fun hideKeyboard()
        fun finishActivity()
    }

    interface Presenter {
        fun onCreate(component: AddCoinComponent, matches: ArrayList<Coin>)
        fun onDestroy()
        fun onFromTextChanged(text: String)
        fun onToTextChanged(text: String)
        fun onFromItemClicked(coin: Coin)
    }
}