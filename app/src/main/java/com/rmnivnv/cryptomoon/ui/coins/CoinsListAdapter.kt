package com.rmnivnv.cryptomoon.ui.coins

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.Coin
import com.rmnivnv.cryptomoon.model.HoldingsHandler
import com.rmnivnv.cryptomoon.model.MultiSelector
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.getChangeColor
import com.rmnivnv.cryptomoon.utils.getStringWithTwoDecimalsFromDouble
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coins_list_item.view.*

/**
 * Created by rmnivnv on 02/07/2017.
 */

class CoinsListAdapter(private val coins: ArrayList<Coin>,
                       private val resProvider: ResourceProvider,
                       private val multiSelector: MultiSelector,
                       private val holdingsHandler: HoldingsHandler,
                       val clickListener: (Coin) -> Unit) : RecyclerView.Adapter<CoinsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.coins_list_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(coins[position], clickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: Coin, listener: (Coin) -> Unit) = with(itemView) {
            setOnClickListener {
                if (multiSelector.atLeastOneIsSelected) {
                    multiSelector.onClick(coin, main_item_layout, coins)
                } else {
                    listener(coin)
                }
            }
            setOnLongClickListener {
                multiSelector.onClick(coin, main_item_layout, coins)
            }
            if (coin.selected) {
                main_item_layout.setBackgroundColor(resProvider.getColor(R.color.colorAccent))
            } else {
                main_item_layout.setBackgroundResource(0)
            }
            main_item_from.text = coin.from
            val to = " / ${coin.to}"
            main_item_to.text = to
            main_item_full_name.text = coin.fullName
            main_item_last_price.text = coin.price
            val chPct24h = "${coin.changePct24h}%"
            main_item_change_in_24.text = chPct24h
            main_item_change_in_24.setTextColor(resProvider.getColor(getChangeColor(coin.changePct24hRaw)))
            main_item_price_arrow.setImageDrawable(resProvider.getDrawable(getChangeArrowDrawable(coin.changePct24hRaw)))
            if (coin.imgUrl.isNotEmpty()) {
                Picasso.with(context)
                        .load(coin.imgUrl)
                        .into(main_item_market_logo)
            }

            val holding = holdingsHandler.isThereSuchHolding(coin.from, coin.to)
            if (holding != null) {
                main_item_holding_qty.text = getStringWithTwoDecimalsFromDouble(holding.quantity)
                val value = "$${getStringWithTwoDecimalsFromDouble(holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holding))}"
                main_item_holding_value.text = value
                main_item_holding_qty.visibility = View.VISIBLE
                main_item_holding_value.visibility = View.VISIBLE
            } else {
                main_item_holding_qty.visibility = View.GONE
                main_item_holding_value.visibility = View.GONE
            }
        }
    }

    private fun getChangeArrowDrawable(change: Float) = when {
        change > 0 -> R.drawable.ic_arrow_drop_up_green
        change == 0f -> R.drawable.ic_remove_orange
        else -> R.drawable.ic_arrow_drop_down_red
    }

    override fun getItemCount() = coins.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position
}