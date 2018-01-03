package com.rmnivnv.cryptomoon.ui.holdings

import com.rmnivnv.cryptomoon.model.HoldingData

/**
 * Created by ivanov_r on 13.09.2017.
 */
interface IHoldings {
    interface View {
        fun updateRecyclerView()
    }
    interface Presenter {
        fun onCreate(holdings: ArrayList<HoldingData>)
        fun onItemSwiped(position: Int?)
        fun onStop()
    }
}