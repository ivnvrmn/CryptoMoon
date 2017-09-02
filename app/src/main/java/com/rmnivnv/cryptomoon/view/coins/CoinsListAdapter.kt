package com.rmnivnv.cryptomoon.view.coins

import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.DisplayCoin
import com.rmnivnv.cryptomoon.model.MultiSelector
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.utils.doubleFromString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.coins_list_item.view.*
import javax.inject.Inject

/**
 * Created by rmnivnv on 02/07/2017.
 */

class CoinsListAdapter(private val coins: ArrayList<DisplayCoin>,
                       component: CoinsComponent,
                       val clickListener: (DisplayCoin) -> Unit) : RecyclerView.Adapter<CoinsListAdapter.ViewHolder>()
{
    init {
        component.inject(this)
    }

    @Inject lateinit var resProvider: ResourceProvider
    @Inject lateinit var multiSelector: MultiSelector

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.coins_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(coins[position], clickListener)
    }

    override fun getItemCount() = coins.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(coin: DisplayCoin, listener: (DisplayCoin) -> Unit) = with(itemView) {
            setOnClickListener {
                if (multiSelector.atLeastOneIsSelected) {
                    multiSelector.onClick(coin, main_item_card, coins)
                } else {
                    listener(coin)
                }
            }
            setOnLongClickListener {
                multiSelector.onClick(coin, main_item_card, coins)
            }
            if (coin.selected) main_item_card.setBackgroundColor(resProvider.getColor(R.color.colorAccent))
            else main_item_card.setBackgroundColor(resProvider.getColor(R.color.colorPrimaryDark))
            main_item_from.text = coin.from
            main_item_to.text = """ / ${coin.to}"""
            main_item_full_name.text = coin.fullName
            main_item_last_price.text = coin.PRICE
            main_item_change_in_24.text = """${coin.CHANGEPCT24HOUR}%"""
            main_item_change_in_24.setTextColor(resProvider.getColor(getChangeColor(doubleFromString(coin.CHANGEPCT24HOUR))))
            main_item_price_arrow.setImageDrawable(resProvider.getDrawable(getChangeArrowDrawable(doubleFromString(coin.CHANGEPCT24HOUR))))
            DrawableCompat.setTint(main_item_price_arrow.drawable, resProvider.getColor(getChangeColor(doubleFromString(coin.CHANGEPCT24HOUR))))
            if (coin.imgUrl.isNotEmpty()) {
                Picasso.with(context)
                        .load(coin.imgUrl)
                        .into(main_item_market_logo)
            }
        }
    }

    private fun getChangeColor(change: Double) = when {
        change > 0 -> R.color.green
        change == 0.0 -> R.color.orange_dark
        else -> R.color.red
    }

    private fun getChangeArrowDrawable(change: Double) = when {
        change > 0 -> R.drawable.ic_arrow_drop_up
        change == 0.0 -> R.drawable.ic_remove
        else -> R.drawable.ic_arrow_drop_down
    }
}