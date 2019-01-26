package com.rmnivnv.cryptomoon.ui.topcoins

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.*
import com.rmnivnv.cryptomoon.ui.coinInfo.CoinInfoActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.top_coins_fragment.top_coins_fragment_swipe_refresh as swipe
import kotlinx.android.synthetic.main.top_coins_fragment.top_coins_fragment_rec_view as recView
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class TopCoinsFragment : DaggerFragment(), TopCoinsContract.View {

    @Inject lateinit var presenter: TopCoinsContract.Presenter
    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var coinsController: CoinsController

    private lateinit var adapter: TopCoinsAdapter
    private var coins: ArrayList<TopCoinData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(coins)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_coins_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        setupSwipeRefresh()
    }

    private fun setupRecView() {
        adapter = TopCoinsAdapter(
            coins,
            resProvider,
            presenter,
            coinsController,
            clickListener = { presenter.onCoinClicked(it) }
        ).also {
            with(recView) {
                layoutManager = LinearLayoutManager(activity)
                adapter = it
            }
        }
    }

    private fun setupSwipeRefresh() {
        swipe.setOnRefreshListener { presenter.onSwipeUpdate() }
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun hideRefreshing() {
        swipe.isRefreshing = false
    }

    override fun startCoinInfoActivity(name: String?) {
        val intent = Intent(context, CoinInfoActivity::class.java)
        intent.putExtra(NAME, name)
        intent.putExtra(TO, USD)
        activity?.startActivity(intent)
    }
}