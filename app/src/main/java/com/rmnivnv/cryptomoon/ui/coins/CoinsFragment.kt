package com.rmnivnv.cryptomoon.ui.coins

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.MultiSelector
import com.rmnivnv.cryptomoon.model.NAME
import com.rmnivnv.cryptomoon.model.TO
import com.rmnivnv.cryptomoon.ui.coinInfo.CoinInfoActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.coins_fragment.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 11/07/2017.
 */
class CoinsFragment : DaggerFragment(), ICoins.View {

    @Inject lateinit var presenter: ICoins.Presenter
    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var multiSelector: MultiSelector

    private lateinit var recView: RecyclerView
    private lateinit var adapter: CoinsListAdapter
    private var coins: ArrayList<DisplayCoin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(coins)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.coins_fragment, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        setupSwipeRefresh()
        presenter.onViewCreated()
    }

    private fun setupRecView() {
        recView = coins_fragment_rec_view
        recView.layoutManager = LinearLayoutManager(activity)
        adapter = CoinsListAdapter(coins, resProvider, multiSelector,
                clickListener = { presenter.onCoinClicked(it) })
        recView.adapter = adapter
    }

    private fun setupSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            presenter.onSwipeUpdate()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    override fun hideRefreshing() {
        swipe_refresh.isRefreshing = false
    }

    override fun enableSwipeToRefresh() {
        swipe_refresh.isEnabled = true
    }

    override fun disableSwipeToRefresh() {
        swipe_refresh.isEnabled = false
    }

    override fun startCoinInfoActivity(name: String?, to: String?) {
        val intent = Intent(context, CoinInfoActivity::class.java)
        intent.putExtra(NAME, name)
        intent.putExtra(TO, to)
        activity.startActivity(intent)
    }

    override fun enableTotalHoldings() {
        coins_fragment_holding_layout.visibility = View.VISIBLE
    }

    override fun disableTotalHoldings() {
        coins_fragment_holding_layout.visibility = View.GONE
    }

    override fun setTotalHoldingsValue(total: String) {
        coins_fragment_total_holdings.text = total
    }

    override fun setTotalHoldingsChangePercent(percent: String) {
        coins_fragment_holdings_all_time_change.text = percent
    }
}