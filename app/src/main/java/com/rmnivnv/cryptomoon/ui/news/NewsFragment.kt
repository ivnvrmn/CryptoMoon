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
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(tweets)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.news_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()
        twitter_login_btn.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                presenter.onSuccessLogin(result)
            }

            override fun failure(exception: TwitterException?) {

            }
        }
        news_swipe_refresh.setOnRefreshListener { presenter.onSwipeUpdate() }
        news_fab.setOnClickListener { presenter.onFabClicked() }
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
        linearLayoutManager = LinearLayoutManager(activity)
        recView.layoutManager = linearLayoutManager
        adapter = NewsAdapter(tweets)
        recView.adapter = adapter
        recView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                presenter.onScrolled(dy, linearLayoutManager.childCount, linearLayoutManager.itemCount, linearLayoutManager.findFirstVisibleItemPosition())
            }
        })
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

    override fun updateInsertedTweets(startPos: Int, count: Int) {
        adapter.notifyItemRangeInserted(startPos, count)
    }

    override fun showLoading() {
        news_loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        news_loading.visibility = View.GONE
    }

    override fun hideSwipeRefreshing() {
        news_swipe_refresh.isRefreshing = false
    }

    override fun showSearchDialog(query: String) {
        val dialog = SearchDialog()
        val bundle = Bundle()
        bundle.putString("query", query)
        dialog.arguments = bundle
        dialog.show(childFragmentManager, "searchDialog")
    }

    override fun showEmptyNews() {
        news_empty_text.visibility = View.VISIBLE
    }

    override fun hideEmptyNews() {
        news_empty_text.visibility = View.GONE
    }

    override fun showFab() {
        news_fab.visibility = View.VISIBLE
    }

    override fun hideFab() {
        news_fab.visibility = View.GONE
    }
}