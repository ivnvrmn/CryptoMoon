package com.rmnivnv.cryptomoon.view.coins.coinInfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.formatter.IAxisValueFormatter

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.app
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_info.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinInfoActivity : AppCompatActivity(), ICoinInfo.View {

    val component by lazy { app.component.plus(CoinInfoModule(this)) }
    @Inject lateinit var presenter: ICoinInfo.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_info)
        component.inject(this)
        setupToolbar()
        setupSpinner()
        presenter.onCreate(component, intent.extras)

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
        Picasso.with(this)
                .load(url)
                .into(coin_info_logo)
    }

    override fun setMainPrice(price: String) {
        coin_info_main_price.text = price
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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
}
