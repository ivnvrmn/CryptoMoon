package com.rmnivnv.cryptomoon.ui.news

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.news_fragment.*
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet
import javax.inject.Inject

/**
 * Created by ivanov_r on 26.09.2017.
 */
class NewsFragment : DaggerFragment(), INews.View {

    @Inject lateinit var presenter: INews.Presenter

    private var tweets: ArrayList<Tweet> = ArrayList()
    private lateinit var recView: RecyclerView
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(tweets)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.news_fragment, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        twitter_login_btn.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                presenter.onSuccessLogin(result)
            }

            override fun failure(exception: TwitterException?) {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun setupRecView() {
        recView = news_rec_view
        recView.layoutManager = LinearLayoutManager(activity)
        adapter = NewsAdapter(tweets)
        recView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        twitter_login_btn.onActivityResult(requestCode, resultCode, data)
    }

    override fun hideLoginBtn() {
        twitter_login_btn.visibility = View.GONE
    }

    override fun showLoginBtn() {
        twitter_login_btn.visibility = View.VISIBLE
    }

    override fun showRecView() {
        news_rec_view.visibility = View.VISIBLE
    }

    override fun updateTweets() {
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        news_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        news_loading.visibility = View.GONE
    }
}