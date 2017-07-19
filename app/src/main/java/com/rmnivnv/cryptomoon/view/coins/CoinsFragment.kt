package com.rmnivnv.cryptomoon.view.coins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.CoinBodyDisplay
import com.rmnivnv.cryptomoon.utils.app
import kotlinx.android.synthetic.main.coins_fragment.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsFragment : Fragment(), ICoins.View {

    val component by lazy { app.component.plus(CoinsModule(this)) }
    @Inject lateinit var presenter: ICoins.Presenter

    private lateinit var recView: RecyclerView
    private lateinit var adapter: CoinsListAdapter
    private var coins: ArrayList<CoinBodyDisplay> = ArrayList()

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
        presenter.onViewCreated(coins)
        setupRecView()
    }

    fun setupRecView() {
        recView = coins_fragment_rec_view
        recView.layoutManager = LinearLayoutManager(activity)
        adapter = CoinsListAdapter(coins, activity)
        recView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }
}