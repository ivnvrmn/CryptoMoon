package com.rmnivnv.cryptomoon.ui.topcoins

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.data.TopCoinEntity
import com.rmnivnv.cryptomoon.utils.*
import com.squareup.picasso.Picasso
import javax.inject.Inject
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_24h_pct as pct24hView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_add_icon as addIconView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_add_layout as addLayoutView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_add_loading as loadingView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_logo as logoView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_market_cap as marketCapView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_name as nameView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_price as priceView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_rank as rankView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_supply as supplyView
import kotlinx.android.synthetic.main.top_coin_item.view.top_coin_volume_24h as volume24hView

/**
 * Created by rmnivnv on 02/09/2017.
 */
class TopCoinsAdapter @Inject constructor(
    private val presenter: TopCoinsContract.Presenter
) : RecyclerView.Adapter<TopCoinsAdapter.ViewHolder>() {

    var coins: List<TopCoinEntity> = listOf()
    var clickListener: (TopCoinEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.top_coin_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(coins[position], position, clickListener)
    }

    override fun getItemCount() = coins.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            coin: TopCoinEntity,
            position: Int,
            listener: (TopCoinEntity) -> Unit
        ) = with(itemView) {
            coin.apply {
                setOnClickListener { listener(this) }

                rankView.text = (position + 1).toString()
                nameView.text = raw.usd.fromSymbol
                marketCapView.text = display.usd.marketCap
                supplyView.text = display.usd.supply
                volume24hView.text = display.usd.volume24Hour
                priceView.text = display.usd.price
                pct24hView.text = display.usd.changePercent24Hour.addPercentSign(raw.usd.changePct24Hour)
                pct24hView.setTextColor(raw.usd.changePct24Hour.toChangeColor(context))

                Picasso.get()
                    .load(display.usd.imageUrl.toCryptoCompareImageUrl())
                    .into(logoView)

                presenter.checkCoinIsAdded(coin) { isAdded ->
                    if (isAdded) {
                        addIconView.setImageDrawable(
                            ContextCompat.getDrawable(context, R.drawable.ic_done)
                        )
                    } else {
                        addIconView.setImageDrawable(
                            ContextCompat.getDrawable(context, R.drawable.ic_add_circle)
                        )
                        addLayoutView.setOnClickListener {
                            loadingView.show()
                            addIconView.hide()
                            presenter.onAddCoinClicked(this)
                        }
                    }
                }
            }
        }
    }
}