package com.rmnivnv.cryptomoon.ui.holdings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.base.BaseActivity
import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.HoldingsHandler
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import kotlinx.android.synthetic.main.activity_holdings.*
import javax.inject.Inject

class HoldingsActivity : BaseActivity(), IHoldings.View {

    @Inject lateinit var presenter: IHoldings.Presenter
    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var holdingsHandler: HoldingsHandler

    private var holdings: ArrayList<HoldingData> = ArrayList()
    private lateinit var recView: RecyclerView
    private lateinit var adapter: HoldingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holdings)
        setupToolbar()
        setupRecView()
        presenter.onCreate(holdings)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.holdings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecView() {
        recView = holdings_rec_view
        recView.layoutManager = LinearLayoutManager(this)
        adapter = HoldingsAdapter(holdings, holdingsHandler, resProvider) {

        }
        recView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                presenter.onItemSwiped(viewHolder?.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(recView)
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
