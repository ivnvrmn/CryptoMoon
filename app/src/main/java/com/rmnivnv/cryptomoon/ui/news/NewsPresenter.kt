package com.rmnivnv.cryptomoon.ui.news

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import javax.inject.Inject

/**
 * Created by ivanov_r on 26.09.2017.
 */
class NewsPresenter @Inject constructor(private val view: INews.View) : INews.Presenter {

    private var tweets: ArrayList<Tweet> = ArrayList()
    private var twitterSession: TwitterSession? = null
    private lateinit var twitterApiClient: TwitterApiClient

    override fun onCreate(tweets: ArrayList<Tweet>) {
        this.tweets = tweets
    }

    override fun onViewCreated() {
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
        view.showLoading()
        val searchService = twitterApiClient.searchService
        val call = searchService.tweets("cryptocurrency", null, null, null, null, null, null, null, null, null)
        call.enqueue(object : Callback<Search>() {
            override fun success(result: Result<Search>?) {
                val resultTweets = result?.data?.tweets
                if (resultTweets != null) {
                    tweets.clear()
                    tweets.addAll(resultTweets)
                    view.updateTweets()
                    view.hideLoading()
                    view.showRecView()
                }
            }

            override fun failure(exception: TwitterException?) {
            }
        })
    }

    override fun onSuccessLogin(result: Result<TwitterSession>?) {
        twitterSession = result?.data
        view.hideLoginBtn()
        twitterApiClient = TwitterCore.getInstance().apiClient
        searchTweets()
    }
}