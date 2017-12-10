package com.rmnivnv.cryptomoon.model

import android.content.Context

/**
 * Created by rmnivnv on 10/12/2017.
 */
class Prefs(context: Context) {

    companion object {
        val PREFS_NAME = "com.rmnivnv.cryptomoon"
        val SEARCH_HASH_TAG = "search_hash_tag"
        val SEARCH_HASH_TAG_DEFAULT = "cryptocurrency"
    }
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var searchHashTag: String
        get() = prefs.getString(SEARCH_HASH_TAG, SEARCH_HASH_TAG_DEFAULT)
        set(value) = prefs.edit().putString(SEARCH_HASH_TAG, value).apply()

}