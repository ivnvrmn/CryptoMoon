package com.rmnivnv.cryptomoon.view.fragments.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmnivnv.cryptomoon.R

/**
 * Created by rmnivnv on 11/07/2017.
 */
class NewsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.news_fragment, container, false)
    }
}