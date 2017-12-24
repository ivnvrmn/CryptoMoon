package com.rmnivnv.cryptomoon.model

import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by rmnivnv on 17/08/2017.
 */
class MultiSelector(val resProvider: ResourceProvider) {

    private val subject = PublishSubject.create<Boolean>()
    var atLeastOneIsSelected = false
        set(value) {
            subject.onNext(value)
            field = value
        }

    fun getSelectorObservable(): Observable<Boolean> = subject

    fun onClick(coin: Coin, card: ViewGroup, coins: ArrayList<Coin>): Boolean {
        coin.selected = setBackgroundAndSelected(coin, card)
        atLeastOneIsSelected = coins.find { it.selected } != null
        return true
    }

    private fun setBackgroundAndSelected(coin: Coin, card: ViewGroup) =
        if (coin.selected) {
            card.setBackgroundColor(0)
            false
        } else {
            card.setBackgroundColor(resProvider.getColor(R.color.colorAccent))
            true
        }
}