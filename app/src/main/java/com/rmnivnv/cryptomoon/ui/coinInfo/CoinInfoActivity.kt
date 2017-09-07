package com.rmnivnv.cryptomoon.ui.coinInfo

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.FROM
import com.rmnivnv.cryptomoon.model.PRICE
import com.rmnivnv.cryptomoon.model.TO
import com.rmnivnv.cryptomoon.ui.addTransaction.AddTransactionActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_coin_info.*
import javax.inject.Inject

class CoinInfoActivity : DaggerAppCompatActivity(), ICoinInfo.View {

    @Inject lateinit var presenter: ICoinInfo.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_info)
        setupToolbar()
        setupSpinner()
        presenter.onCreate(intent.extras)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupSpinner() {
        coin_info_graph_periods.adapter = ArrayAdapter<String>(this, R.layout.period_item, R.id.period,
                resProvider.getStringArray(R.array.histo_periods))
        coin_info_graph_periods.setSelection(5)
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                presenter.onSpinnerItemClicked(coin_info_graph_periods.selectedItem.toString())

            }
        }
        coin_info_graph_periods.onItemSelectedListener = listener
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setLogo(url: String) {
        if (url.isNotEmpty()) {
            Picasso.with(this)
                    .load(url)
                    .into(coin_info_logo)
        }
    }

    override fun setMainPrice(price: String) {
        coin_info_main_price.text = price
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.coin_info_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.coin_info_menu_add_trans -> presenter.onAddTransactionClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun drawChart(line: CandleData) {
        val xAxis = coin_info_graph.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //TODO set date
//        val format = SimpleDateFormat("dd MMM", Locale.getDefault())
//        xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
//            format.format(Date(value.toLong() * 1000))
//        }
        coin_info_graph.data = line
        coin_info_graph.invalidate()
    }

    override fun setOpen(open: String) {
        coin_info_open.text = open
    }

    override fun setHigh(high: String) {
        coin_info_high.text = high
    }

    override fun setLow(low: String) {
        coin_info_low.text = low
    }

    override fun setChange(change: String) {
        coin_info_change.text = change
    }

    override fun setChangePct(pct: String) {
        coin_info_change_pct.text = pct
    }

    override fun setSupply(supply: String) {
        coin_info_supply.text = supply
    }

    override fun setMarketCap(cap: String) {
        coin_info_market_cap.text = cap
    }

    override fun enableGraphLoading() {
        coin_info_loading.visibility = View.VISIBLE
        coin_info_graph.visibility = View.GONE
    }

    override fun disableGraphLoading() {
        coin_info_loading.visibility = View.GONE
        coin_info_graph.visibility = View.VISIBLE
    }

    override fun startAddTransactionActivity(from: String?, to: String?, price: String?) {
        val intent = Intent(this, AddTransactionActivity::class.java)
        intent.putExtra(FROM, from)
        intent.putExtra(TO, to)
        intent.putExtra(PRICE, price)
        startActivity(intent)
    }
}
