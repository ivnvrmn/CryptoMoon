package com.rmnivnv.cryptomoon.view.coins

import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coins_list_item.view.*

/**
 * Created by rmnivnv on 02/07/2017.
 */

class CoinsListAdapter(private val items: ArrayList<DisplayCoin>,
                       private val resProvider: ResourceProvider,
                       val clickListener: (DisplayCoin) -> Unit,
                       val longClickListener: (DisplayCoin) -> Boolean) :
        RecyclerView.Adapter<CoinsListAdapter.ViewHolder>()
{
    lateinit var popMenuAnchor: View

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.coins_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(items[position], clickListener, longClickListener)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: DisplayCoin, listener: (DisplayCoin) -> Unit,
                      longClickListener: (DisplayCoin) -> Boolean) = with(itemView) {

            setOnClickListener { listener(coin) }
            setOnLongClickListener {
                popMenuAnchor = main_item_market_logo
                longClickListener(coin)
            }
            main_item_market.text = """${coin.from}/${coin.to}"""
            main_item_last_price.text = coin.PRICE
            main_item_change_in_24.text = """${coin.CHANGEPCT24HOUR}%"""
            main_item_change_in_24.setTextColor(resProvider.getColor(getChangeColor(coin.CHANGEPCT24HOUR.toDouble())))
            main_item_price_arrow.setImageDrawable(resProvider.getDrawable(getChangeArrowDrawable(coin.CHANGEPCT24HOUR.toDouble())))
            DrawableCompat.setTint(main_item_price_arrow.drawable, resProvider.getColor(getChangeColor(coin.CHANGEPCT24HOUR.toDouble())))
            if (!coin.imgUrl.isNullOrEmpty()) {
                Picasso.with(context)
                        .load(coin.imgUrl)
                        .into(main_item_market_logo)
            }
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