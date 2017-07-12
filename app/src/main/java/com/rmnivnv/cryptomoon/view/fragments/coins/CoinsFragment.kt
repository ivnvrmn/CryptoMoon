package com.rmnivnv.cryptomoon.view.fragments.coins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.Market
import com.rmnivnv.cryptomoon.utils.app
import kotlinx.android.synthetic.main.coins_fragment.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsFragment : Fragment(), ICoins.View {

    val component by lazy { app.component.plus(CoinsModule(this)) }
    @Inject lateinit var presenter: ICoins.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.onCreate(component)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.coins_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
        coins_fragment_rec_view.layoutManager = LinearLayoutManager(activity)
        coins_fragment_rec_view.adapter = CoinsListAdapter(mockMarketList(), activity)
    }

    private fun mockMarketList(): ArrayList<Market> {
        val result: ArrayList<Market> = ArrayList()
        result.add(Market("BTC", "USD", 2517, 72.123, 3.2412466, R.drawable.ic_btc))
        result.add(Market("ETH", "USD", 278, -25.56, 83.2300596, R.drawable.ic_eth))
        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}