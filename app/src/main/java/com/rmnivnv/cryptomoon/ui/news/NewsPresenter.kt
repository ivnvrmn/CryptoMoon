package com.rmnivnv.cryptomoon.ui.news

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.rmnivnv.cryptomoon.model.Prefs
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.rxbus.SearchHashTagUpdated
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by ivanov_r on 26.09.2017.
 */
class NewsPresenter @Inject constructor(private val view: INews.View,
                                        context: Context) : INews.Presenter {

    private var tweets: ArrayList<Tweet> = ArrayList()
    private var twitterSession: TwitterSession? = null
    private var twitterApiClient: TwitterApiClient? = null
    private var call: Call<Search>? = null
    private var isSwipeRefreshing = false
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var loading = true
    private var lastId: Long? = null
    private val prefs = Prefs(context)
    private val disposable = CompositeDisposable()

    override fun onCreate(tweets: ArrayList<Tweet>) {
        this.tweets = tweets
        disposable.add(RxBus.listen(SearchHashTagUpdated::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchHashTagUpdated() }))
    }

    private fun searchHashTagUpdated() {
        tweets.clear()
        initTwitterAndSearchTweets()
    }

    override fun onStart() {
        if (tweets.isNotEmpty()) {
            view.showRecView()
        } else {
            initTwitterAndSearchTweets()
        }
    }

    private fun initTwitterAndSearchTweets() {
        val session = initTwitterSession()
        if (session != null) {
            twitterApiClient = TwitterApiClient(session)
            TwitterCore.getInstance().addApiClient(session, twitterApiClient)
            searchTweets()
        } else {
            view.showLoginBtn()
        }
    }

    private fun initTwitterSession() = TwitterCore.getInstance().sessionManager.activeSession ?: null

    private fun searchTweets() {
        view.hideEmptyNews()
        if (!isSwipeRefreshing) view.showLoading()
        val searchService = twitterApiClient?.searchService
        call = searchService?.tweets(prefs.searchHashTag, null, null, null, null, null, null, null, lastId, null)
        call?.enqueue(object : Callback<Search>() {
            override fun success(result: Result<Search>?) {
                val resultTweets = result?.data?.tweets
                if (resultTweets != null) {
                    tweets.addAll(resultTweets)
                    if (lastId != null) tweets.remove(tweets.find { it.id == lastId })
                    view.updateTweets()
                    loading = true
                }
                afterSearch()
            }

            override fun failure(exception: TwitterException?) {

            }
        })
    }

    private fun afterSearch() {
        if (isSwipeRefreshing) {
            isSwipeRefreshing = false
            view.hideSwipeRefreshing()
        } else {
            view.hideLoading()
            view.showRecView()
        }
        if (tweets.isEmpty()) {
            view.showEmptyNews()
        } else {
            view.hideEmptyNews()
        }
    }

    override fun onSuccessLogin(result: Result<TwitterSession>?) {
        twitterSession = result?.data
        view.hideLoginBtn()
        twitterApiClient = TwitterCore.getInstance().apiClient
        searchTweets()
    }

    override fun onStop() {
        call?.cancel()
        disposable.clear()
    }

    override fun onSwipeUpdate() {
        isSwipeRefreshing = true
        initTwitterAndSearchTweets()
    }

    override fun onScrolled(dy: Int, linearLayoutManager: LinearLayoutManager) {
        if (dy > 0) {
            visibleItemCount = linearLayoutManager.childCount
            totalItemCount = linearLayoutManager.itemCount
            pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
            if (loading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                loading = false
                lastId = tweets.last().id
                searchTweets()
            }
        }
    }

    override fun onFabClicked() {
        view.showSearchDialog()
    }
}