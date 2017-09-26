package com.rmnivnv.cryptomoon.ui.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetView
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by ivanov_r on 26.09.2017.
 */
class NewsAdapter(private val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.news_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(tweets[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(tweet: Tweet) = with(itemView) {
            news_item_layout.addView(TweetView(context, tweet))
        }
    }

    override fun getItemCount() = tweets.size
}