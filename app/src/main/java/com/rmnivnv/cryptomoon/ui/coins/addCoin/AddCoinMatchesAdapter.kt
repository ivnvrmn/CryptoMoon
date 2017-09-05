package com.rmnivnv.cryptomoon.ui.coins.addCoin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.InfoCoin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.add_coin_matches_item.view.*

/**
 * Created by rmnivnv on 30/07/2017.
 */
class AddCoinMatchesAdapter(private val items: ArrayList<InfoCoin>, private val context: Context,
                            val listener: (InfoCoin) -> Unit) : RecyclerView.Adapter<AddCoinMatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AddCoinMatchesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.add_coin_matches_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(items[position], listener)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: InfoCoin, listener: (InfoCoin) -> Unit) = with(itemView) {
            add_coin_name.text = coin.coinName
            add_coin_short_name.text = coin.name
            if (!coin.imageUrl.isEmpty()) {
                Picasso.with(context)
                        .load(coin.imageUrl)
                        .into(add_coin_icon)
            } else {
                add_coin_icon.visibility = View.INVISIBLE
            }
            setOnClickListener { listener(coin) }
        }
    }
}