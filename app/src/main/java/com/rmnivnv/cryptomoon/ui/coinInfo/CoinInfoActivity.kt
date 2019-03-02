package com.rmnivnv.cryptomoon.ui.coinInfo

import android.content.Context
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
import com.rmnivnv.cryptomoon.base.BaseActivity
import com.rmnivnv.cryptomoon.model.FROM
import com.rmnivnv.cryptomoon.model.PRICE
import com.rmnivnv.cryptomoon.model.TO
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.ui.addTransaction.AddTransactionActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.hide
import com.rmnivnv.cryptomoon.utils.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_empty_graph as emptyGraph
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_holdings_layout as holdingLayout
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_holding_trade_date as holdingTradeDate
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_holding_trade_price as holdingTradePrice
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_holding_profit_value as holdingProfitValue
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_profit_loss as holdingProfitLoss
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_holding_ch_pct as holdingChangePct
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_value as valueView
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_quantity as quantityView
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_loading as graphLoading
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_market_cap as marketCap
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_supply as supplyView
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_change_pct as changePct24
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_change as change24
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_low as low24
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_high as high24
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_open as open24
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_graph as coinGraph
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_main_price as mainPrice
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_logo as coinLogo
import kotlinx.android.synthetic.main.activity_coin_info.coin_info_graph_periods as graphPeriods
import javax.inject.Inject

class CoinInfoActivity : BaseActivity(), CoinInfoContract.View {

    @Inject lateinit var presenter: CoinInfoContract.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_info)
        setupToolbar()
        presenter.onCreate(intent.extras.getParcelable(KEY_COIN))
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setupSpinner() {
        graphPeriods.adapter = ArrayAdapter<String>(this, R.layout.period_item, R.id.period,
                resProvider.getStringArray(R.array.histo_periods))
        graphPeriods.setSelection(5)
        graphPeriods.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                presenter.onSpinnerItemClicked(p2)
            }
        }
    }

    override fun showTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun showLogo(url: String) {
        if (url.isNotEmpty()) {
            Picasso.get().load(url).into(coinLogo)
        }
    }

    override fun showMainPrice(price: String) {
        mainPrice.text = price
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
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
        val xAxis = coinGraph.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //TODO set date
//        val format = SimpleDateFormat("dd MMM", Locale.getDefault())
//        xAxis.valueFormatter = IAxisValueFormatter { value, axis ->
//            format.format(Date(value.toLong() * 1000))
//        }
        coinGraph.data = line
        coinGraph.invalidate()
    }

    override fun showOpen24Hour(open: String) {
        open24.text = open
    }

    override fun showHigh24Hour(high: String) {
        high24.text = high
    }

    override fun showLow24Hour(low: String) {
        low24.text = low
    }

    override fun showChange24Hour(change: String) {
        change24.text = change
    }

    override fun showChangePct24Hour(pct: String) {
        changePct24.text = pct
    }

    override fun showSupply(supply: String) {
        supplyView.text = supply
    }

    override fun showMarketCap(cap: String) {
        marketCap.text = cap
    }

    override fun enableGraphLoading() {
        graphLoading.show()
        coinGraph.hide()
    }

    override fun disableGraphLoading() {
        graphLoading.hide()
        coinGraph.show()
    }

    override fun startAddTransactionActivity(from: String?, to: String?, price: String?) {
        val intent = Intent(this, AddTransactionActivity::class.java)
        intent.putExtra(FROM, from)
        intent.putExtra(TO, to)
        intent.putExtra(PRICE, price)
        startActivity(intent)
    }

    override fun setHoldingQuantity(quantity: String) {
        quantityView.text = quantity
    }

    override fun setHoldingValue(value: String) {
        valueView.text = value
    }

    override fun setHoldingChangePercent(pct: String) {
        holdingChangePct.text = pct
    }

    override fun setHoldingProfitLoss(profitLoss: String) {
        holdingProfitLoss.text = profitLoss
    }

    override fun setHoldingProfitValue(value: String) {
        holdingProfitValue.text = value
    }

    override fun setHoldingTradePrice(price: String) {
        holdingTradePrice.text = price
    }

    override fun setHoldingTradeDate(date: String) {
        holdingTradeDate.text = date
    }

    override fun enableHoldings() {
        holdingLayout.show()
    }

    override fun disableHoldings() {
        holdingLayout.hide()
    }

    override fun setHoldingChangePercentColor(color: Int) {
        holdingChangePct.setTextColor(resProvider.getColor(color))
    }

    override fun setHoldingProfitValueColor(color: Int) {
        holdingProfitValue.setTextColor(resProvider.getColor(color))
    }

    override fun enableEmptyGraphText() {
        emptyGraph.show()
    }

    override fun disableEmptyGraphText() {
        emptyGraph.hide()
    }

    companion object {
        private const val KEY_COIN = "coin"

        fun createIntent(context: Context, coin: CoinEntity): Intent {
            return Intent(context, CoinInfoActivity::class.java)
                .putExtra(KEY_COIN, coin)
        }
    }
}
