package com.rmnivnv.cryptomoon.ui.topcoins

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.CoinsController
import com.rmnivnv.cryptomoon.model.TopCoinData
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.addCommasToStringNumber
import com.rmnivnv.cryptomoon.utils.getChangeColor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_logo as logoView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_add_layout as addLayoutView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_add_icon as addIconView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_volume_24h as volume24hView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_supply as supplyView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_market_cap as marketCapView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_24h_pct as pct24hView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_price as priceView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_name as nameView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_rank as rankView
import javax.inject.Inject

/**
 * Created by rmnivnv on 02/09/2017.
 */
class TopCoinsAdapter @Inject constructor(
    private val presenter: TopCoinsContract.Presenter,
    private val resProvider: ResourceProvider,
    private val coinsController: CoinsController
) : RecyclerView.Adapter<TopCoinsAdapter.ViewHolder>() {

    var coins: List<TopCoinData> = listOf()
    var clickListener: (TopCoinData) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.top_coin_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(coins[position], clickListener)
    }

    override fun getItemCount() = coins.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: TopCoinData, listener: (TopCoinData) -> Unit) = with(itemView) {
            coin.apply {
                setOnClickListener { listener(this) }

                rankView.text = rank.toString()
                nameView.text = name
                priceView.text = price_usd

                val pctCh24h = percent_change_24h ?: ""
                if (pctCh24h.isNotEmpty()) {
                    val value = "$pctCh24h%"
                    pct24hView.text = value
                    pct24hView.setTextColor(
                        resProvider.getColor(getChangeColor(pctCh24h.replace(",", "").toFloat()))
                    )
                }

                marketCapView.text = addCommasToStringNumber(market_cap_usd)
                supplyView.text = addCommasToStringNumber(total_supply)
                volume24hView.text = addCommasToStringNumber(vol24Usd)

                if (!imgUrl.isNullOrEmpty()) {
                    Picasso.get().load(imgUrl).into(logoView)
                }

                if (coinsController.coinIsAdded(this)) {
                    addIconView.setImageDrawable(resProvider.getDrawable(R.drawable.ic_done))
                } else {
                    addIconView.setImageDrawable(resProvider.getDrawable(R.drawable.ic_add_circle))
                    addLayoutView.setOnClickListener { presenter.onAddCoinClicked(this, itemView) }
                }
            }
        }
    }
}