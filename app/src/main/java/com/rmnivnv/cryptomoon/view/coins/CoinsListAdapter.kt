package com.rmnivnv.cryptomoon.view.coins

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.CoinBodyDisplay
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coins_list_item.view.*

/**
 * Created by rmnivnv on 02/07/2017.
 */

class CoinsListAdapter(private val items: ArrayList<CoinBodyDisplay>, private val context: Context) : RecyclerView.Adapter<CoinsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.coins_list_item, parent, false)
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
            itemView.main_item_change_in_24.text = """${coin.CHANGEPCT24HOUR}%"""
            itemView.main_item_change_in_24.setTextColor(ContextCompat.getColor(context,
                    getChangeColor(coin.CHANGEPCT24HOUR?.toDouble())))
            itemView.main_item_price_arrow.setImageDrawable(ContextCompat.getDrawable(context,
                    getChangeArrowDrawable(coin.CHANGEPCT24HOUR?.toDouble())))
            DrawableCompat.setTint(itemView.main_item_price_arrow.drawable, ContextCompat.getColor(context,
                    getChangeColor(coin.CHANGEPCT24HOUR?.toDouble())))
            if (coin.imgUrl != null) Picasso.with(context).load(coin.imgUrl).into(itemView.main_item_market_logo)
        }
    }

    private fun getChangeColor(change: Double?): Int {
        if (change != null && change > 0) {
            return R.color.green
        } else if (change != null && change == 0.0) {
            return R.color.orange_dark
        } else {
            return R.color.red
        }
    }

    private fun getChangeArrowDrawable(change: Double?): Int {
        if (change != null && change > 0) {
            return R.drawable.ic_arrow_drop_up
        } else if (change != null && change == 0.0) {
            return R.drawable.ic_remove
        } else {
            return R.drawable.ic_arrow_drop_down
        }
    }
}