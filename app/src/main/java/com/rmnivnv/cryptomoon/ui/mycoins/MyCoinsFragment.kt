package com.rmnivnv.cryptomoon.ui.mycoins

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.HoldingsHandler
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.ui.coinInfo.CoinInfoActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.hide
import com.rmnivnv.cryptomoon.utils.show
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.coins_fragment.coins_fragment_empty_text as emptyText
import kotlinx.android.synthetic.main.coins_fragment.coins_fragment_rec_view as recyclerView
import kotlinx.android.synthetic.main.coins_fragment.swipe_refresh as swipeRefresh

/**
 * Created by rmnivnv on 11/07/2017.
 */
class MyCoinsFragment : DaggerFragment(), MyCoinsContract.View {

    @Inject lateinit var presenter: MyCoinsContract.Presenter
    @Inject lateinit var adapter: MyCoinsAdapter
    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var holdingsHandler: HoldingsHandler

    private lateinit var recView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.coins_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        setupSwipeRefresh()
    }

    private fun setupRecView() {
        recView = recyclerView
        recView.layoutManager = LinearLayoutManager(activity)
        recView.adapter = adapter.apply {
            clickListener = { presenter.onCoinClicked(it) }
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener { presenter.onSwipeUpdate() }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun updateMyCoins(myCoins: List<CoinEntity>) {
        with(adapter) {
            coins = myCoins
            notifyDataSetChanged()
        }
    }

    override fun hideRefreshing() {
        swipeRefresh.isRefreshing = false
    }

    override fun showSwipeToRefresh() {
        swipeRefresh.isEnabled = true
    }

    override fun hideSwipeToRefresh() {
        swipeRefresh.isEnabled = false
    }

    override fun showCoinInfoActivity(coin: CoinEntity) {
        context?.also {
            startActivity(CoinInfoActivity.createIntent(it, coin))
        }
    }

    override fun showEmptyText() {
        emptyText.show()
    }

    override fun hideEmptyText() {
        emptyText.hide()
    }
}