package com.rmnivnv.cryptomoon.ui.mycoins

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.di.CRYPTO_COMPARE_IMAGE_URL
import com.rmnivnv.cryptomoon.model.data.CoinEntity
import com.rmnivnv.cryptomoon.utils.addPercentSign
import com.rmnivnv.cryptomoon.utils.toChangeArrowDrawable
import com.rmnivnv.cryptomoon.utils.toChangeColor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coins_list_item.view.main_item_change_in_24 as changePercent24h
import kotlinx.android.synthetic.main.coins_list_item.view.main_item_from as fromSymbol
import kotlinx.android.synthetic.main.coins_list_item.view.main_item_last_price as price
import kotlinx.android.synthetic.main.coins_list_item.view.main_item_market_logo as logo
import kotlinx.android.synthetic.main.coins_list_item.view.main_item_price_arrow as changeArrow

/**
 * Created by rmnivnv on 02/07/2017.
 */

class MyCoinsAdapter : RecyclerView.Adapter<MyCoinsAdapter.ViewHolder>() {
    var coins: List<CoinEntity> = listOf()
    var clickListener: (CoinEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.coins_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(coins[position], clickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            coin: CoinEntity,
            listener: (CoinEntity) -> Unit
        ) = with(itemView) {
            coin.apply {
                setOnClickListener { listener(coin) }

                fromSymbol.text = raw.usd.fromSymbol
                price.text = display.usd.price
                changePercent24h.text = display.usd.changePercent24Hour.addPercentSign(
                    raw.usd.changePct24Hour
                )
                changePercent24h.setTextColor(raw.usd.changePct24Hour.toChangeColor(context))
                changeArrow.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        raw.usd.changePct24Hour.toChangeArrowDrawable()
                    )
                )

                Picasso.get()
                    .load("$CRYPTO_COMPARE_IMAGE_URL${display.usd.imageUrl}")
                    .into(logo)
            }
        }
    }

    override fun getItemCount() = coins.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position
}