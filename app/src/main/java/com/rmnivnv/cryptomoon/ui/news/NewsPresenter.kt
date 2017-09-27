package com.rmnivnv.cryptomoon.ui.news

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by ivanov_r on 26.09.2017.
 */
class NewsPresenter @Inject constructor(private val view: INews.View) : INews.Presenter {

    private var tweets: ArrayList<Tweet> = ArrayList()
    private var twitterSession: TwitterSession? = null
    private lateinit var twitterApiClient: TwitterApiClient
    private var call: Call<Search>? = null
    private var isSwipeRefreshing = false

    override fun onCreate(tweets: ArrayList<Tweet>) {
        this.tweets = tweets
    }

    override fun onStart() {
        val activeSession = TwitterCore.getInstance().sessionManager.activeSession
        if (activeSession != null) {
            twitterApiClient = TwitterApiClient(activeSession)
            TwitterCore.getInstance().addApiClient(activeSession, twitterApiClient)
            searchTweets()
        } else {
            view.showLoginBtn()
        }
    }

    private fun searchTweets() {
        if (!isSwipeRefreshing) view.showLoading()
        val searchService = twitterApiClient.searchService
        call = searchService.tweets("cryptocurrency", null, null, null, null, null, null, null, null, null)
        call?.enqueue(object : Callback<Search>() {
            override fun success(result: Result<Search>?) {
                val resultTweets = result?.data?.tweets
                if (resultTweets != null) {
                    tweets.clear()
                    tweets.addAll(resultTweets)
                    view.updateTweets()
                }
                afterSearch()
            }

            override fun failure(exception: TwitterException?) {
                afterSearch()
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
    }

    override fun onSuccessLogin(result: Result<TwitterSession>?) {
        twitterSession = result?.data
        view.hideLoginBtn()
        twitterApiClient = TwitterCore.getInstance().apiClient
        searchTweets()
    }

    override fun onStop() {
        call?.cancel()
    }

    override fun onSwipeUpdate() {
        isSwipeRefreshing = true
        searchTweets()
    }
}