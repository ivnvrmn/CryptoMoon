package com.rmnivnv.cryptomoon.view.coins.addCoin

import com.rmnivnv.cryptomoon.model.Coin

/**
 * Created by rmnivnv on 27/07/2017.
 */
interface IAddCoin {

    interface View {
        fun updateRecyclerView()
        fun setMatchesResultSize(matchesCount: String)
    }

    interface Presenter {
        fun onCreate(component: AddCoinComponent, matches: ArrayList<Coin>)
        fun onDestroy()
        fun onFromTextChanged(text: String)
        fun onToTextChanged(text: String)
        fun onFromItemClicked()
    }
}