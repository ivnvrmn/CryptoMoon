package com.rmnivnv.cryptomoon.ui.addCoin

import com.rmnivnv.cryptomoon.model.InfoCoin
import io.reactivex.Observable

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
        fun clearFromEdt()
    }

    interface Presenter {
        fun onCreate(matches: ArrayList<InfoCoin>)
        fun onFromItemClicked(coin: InfoCoin)
        fun observeFromText(observable: Observable<CharSequence>)
        fun onStop()
    }
}