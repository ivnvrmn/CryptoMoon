package com.rmnivnv.cryptomoon.ui.holdings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.DEFAULT_DATE_FORMAT
import com.rmnivnv.cryptomoon.model.HoldingData
import com.rmnivnv.cryptomoon.model.HoldingsHandler
import com.rmnivnv.cryptomoon.utils.formatLongDateToString
import kotlinx.android.synthetic.main.holdings_item.view.*

/**
 * Created by ivanov_r on 13.09.2017.
 */
class HoldingsAdapter(private val holdings: ArrayList<HoldingData>,
                      private val holdingsHandler: HoldingsHandler,
                      val clickListener: (HoldingData) -> Unit) : RecyclerView.Adapter<HoldingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.holdings_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(holdings[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(holdingData: HoldingData) = with(itemView) {
            holdings_item_from_to.text = "${holdingData.from} / ${holdingData.to}"
            holdings_item_trade_price.text = "$${holdingData.price}"
            holdings_item_trade_date.text = formatLongDateToString(holdingData.date, DEFAULT_DATE_FORMAT)
            holdings_item_quantity.text = holdingData.quantity.toString()
            holdings_item_current_total.text = holdingsHandler.getTotalValueWithCurrentPriceByHoldingData(holdingData).toString()

        }
    }

    override fun getItemCount() = holdings.size
}