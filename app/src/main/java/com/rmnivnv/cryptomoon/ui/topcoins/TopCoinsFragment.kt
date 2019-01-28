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
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.top_coins_fragment.top_coins_fragment_swipe_refresh as swipe
import kotlinx.android.synthetic.main.top_coins_fragment.top_coins_fragment_rec_view as recView
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class TopCoinsFragment : DaggerFragment(), TopCoinsContract.View {

    @Inject lateinit var presenter: TopCoinsContract.Presenter
    @Inject lateinit var adapter: TopCoinsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_coins_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        with(recView) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@TopCoinsFragment.adapter.apply {
                clickListener = { presenter.onCoinClicked(it) }
            }
        }
    }

    private fun setupSwipeRefresh() {
        swipe.setOnRefreshListener { presenter.onSwiped() }
    }

    override fun updateList(topCoins: List<TopCoinData>) {
        with(adapter) {
            coins = topCoins
            notifyDataSetChanged()
        }
    }

    override fun updateItem(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun hideRefreshing() {
        swipe.isRefreshing = false
    }

    override fun startCoinInfoActivity(name: String?) {
        activity?.startActivity(
            Intent(context, CoinInfoActivity::class.java)
                .putExtra(NAME, name)
                .putExtra(TO, USD)
        )
    }
}