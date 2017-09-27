package com.rmnivnv.cryptomoon.ui.news

import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet

/**
 * Created by ivanov_r on 26.09.2017.
 */
interface INews {

    interface View {
        fun hideLoginBtn()
        fun showLoginBtn()
        fun showRecView()
        fun updateTweets()
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun onCreate(tweets: ArrayList<Tweet>)
        fun onSuccessLogin(result: Result<TwitterSession>?)
        fun onStart()
        fun onStop()
    }

}