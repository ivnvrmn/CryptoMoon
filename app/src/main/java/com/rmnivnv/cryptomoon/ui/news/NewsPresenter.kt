package com.rmnivnv.cryptomoon.ui.news

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.rmnivnv.cryptomoon.model.Prefs
import com.rmnivnv.cryptomoon.model.rxbus.RxBus
import com.rmnivnv.cryptomoon.model.rxbus.SearchHashTagUpdated
import com.rmnivnv.cryptomoon.utils.logDebug
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
                                        private val context: Context) : INews.Presenter {

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
    private var newTweetsStartPosition: Int = 0
    private val prefs = Prefs(context)
    private val disposable = CompositeDisposable()
    private var isFooterLoading = false

    override fun onCreate(tweets: ArrayList<Tweet>) {
        this.tweets = tweets
    }

    override fun onStart() {
        if (tweets.isNotEmpty()) {
            view.showRecView()
        } else {
            initTwitterAndSearchTweets()
        }
        disposable.add(RxBus.listen(SearchHashTagUpdated::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchHashTagUpdated() }))
    }

    private fun searchHashTagUpdated() {
        tweets.clear()
        newTweetsStartPosition = 0
        isFooterLoading = false
        initTwitterAndSearchTweets()
    }

    private fun initTwitterAndSearchTweets() {
        val session = initTwitterSession()
        if (session != null) {
            twitterApiClient = TwitterApiClient(session)
            TwitterCore.getInstance().addApiClient(session, twitterApiClient)
            searchTweets()
        } else {
            view.showLoginBtn()
            view.hideFab()
        }
    }

    private fun initTwitterSession() = TwitterCore.getInstance().sessionManager.activeSession ?: null

    private fun searchTweets() {
        view.hideEmptyNews()
        if (!isSwipeRefreshing && !isFooterLoading) view.showLoading()
        val searchService = twitterApiClient?.searchService
        call = searchService?.tweets(
                prefs.searchHashTag,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                lastId,
                null)
        call?.enqueue(object : Callback<Search>() {
            override fun success(result: Result<Search>?) {
                val resultTweets = result?.data?.tweets
                if (resultTweets != null) {
                    tweets.addAll(resultTweets)
                    if (lastId != null) tweets.remove(tweets.find { it.id == lastId })
                    view.updateInsertedTweets(newTweetsStartPosition, 15)
                    loading = true
                }
                afterSearch()
            }

            override fun failure(exception: TwitterException?) {
                context.logDebug("searchTweets exception = " + exception.toString())
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
        view.showFab()
        twitterApiClient = TwitterCore.getInstance().apiClient
        searchTweets()
    }

    override fun onStop() {
        call?.cancel()
        disposable.clear()
    }

    override fun onSwipeUpdate() {
        isSwipeRefreshing = true
        isFooterLoading = false
        initTwitterAndSearchTweets()
    }

    override fun onScrolled(dy: Int, linearLayoutManager: LinearLayoutManager) {
        if (dy > 0) {
            visibleItemCount = linearLayoutManager.childCount
            totalItemCount = linearLayoutManager.itemCount
            pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
            if (loading && (visibleItemCount + pastVisibleItems) >= totalItemCount - 3) {
                loading = false
                lastId = tweets.last().id
                newTweetsStartPosition = tweets.size + 1
                isFooterLoading = true
                searchTweets()
            }
        }
    }

    override fun onFabClicked() {
        view.showSearchDialog()
    }
}