package com.rmnivnv.cryptomoon.view.fragments.coins

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.Market
import com.rmnivnv.cryptomoon.model.CoinBodyDisplay
import kotlinx.android.synthetic.main.main_list_item.view.*

/**
 * Created by rmnivnv on 02/07/2017.
 */

class CoinsListAdapter(private val items: ArrayList<CoinBodyDisplay>, private val context: Context) : RecyclerView.Adapter<CoinsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: CoinBodyDisplay) {
            itemView.main_item_market.text = """${coin.from}/${coin.to}"""
            itemView.main_item_last_price.text = coin.PRICE
            //itemView.main_item_market_logo.setImageDrawable(ContextCompat.getDrawable(context, market.logo))
            itemView.main_item_change_in_24.text = """${coin.CHANGEPCT24HOUR}%"""
            itemView.main_item_change_in_24.setTextColor(ContextCompat.getColor(context, setChangePercentColor(coin.CHANGEPCT24HOUR?.toDouble())))
            //itemView.main_item_holding.text = market.hold.toString()
        }
    }

    private fun setChangePercentColor(change: Double?): Int {
        if (change != null && change >= 0) R.color.green else R.color.red
        return R.color.black
    }
}