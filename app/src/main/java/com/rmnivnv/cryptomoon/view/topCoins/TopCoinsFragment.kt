package com.rmnivnv.cryptomoon.view.topCoins

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.TopCoinData
import com.rmnivnv.cryptomoon.utils.app
import kotlinx.android.synthetic.main.top_coins_fragment.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class TopCoinsFragment : Fragment(), ITopCoins.View {

    val component by lazy { app.component.plus(TopCoinsModule(this)) }
    @Inject lateinit var presenter: ITopCoins.Presenter

    private lateinit var recView: RecyclerView
    private lateinit var adapter: TopCoinsAdapter
    private var coins: ArrayList<TopCoinData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.onCreate(component, coins)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.top_coins_fragment, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        setupSwipeRefresh()
    }

    private fun setupRecView() {
        recView = top_coins_fragment_rec_view
        recView.layoutManager = LinearLayoutManager(activity)
        adapter = TopCoinsAdapter(coins, component,
                clickListener = { presenter.onCoinClicked(it) })
        recView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        top_coins_fragment_swipe_refresh.setOnRefreshListener {
            presenter.onSwipeUpdate()
        }
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun hideRefreshing() {
        top_coins_fragment_swipe_refresh.isRefreshing = false
    }
}