package com.rmnivnv.cryptomoon.ui.addCoin

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.model.InfoCoin
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_add_coin.*
import javax.inject.Inject

class AddCoinActivity : DaggerAppCompatActivity(), IAddCoin.View {

    @Inject lateinit var presenter: IAddCoin.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    private lateinit var recView: RecyclerView
    private lateinit var adapter: AddCoinMatchesAdapter
    private var matches: ArrayList<InfoCoin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_coin)
        setupToolbar()
        setupEdts()
        setupRecView()
        presenter.onCreate(matches)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resProvider.getString(R.string.add_coin)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupEdts() {
        add_coin_from_edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onFromTextChanged(p0.toString())
            }
        })
    }

    private fun setupRecView() {
        recView = add_coin_matches_rec_view
        recView.layoutManager = LinearLayoutManager(this)
        adapter = AddCoinMatchesAdapter(matches, this) {
            presenter.onFromItemClicked(it)
        }
        recView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun updateRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    override fun setMatchesResultSize(matchesCount: String) {
        val text = matchesCount + " " + resProvider.getString(R.string.matches_found)
        add_coin_matches_count.text = text
    }

    override fun disableMatchesCount() {
        add_coin_matches_count.visibility = View.GONE
    }

    override fun enableMatchesCount() {
        add_coin_matches_count.visibility = View.VISIBLE
    }

    override fun enableLoadingLayout() {
        loading_layout.visibility = View.VISIBLE
        add_coin_from_edt.isEnabled = false
    }

    override fun disableLoadingLayout() {
        loading_layout.visibility = View.GONE
        add_coin_from_edt.isEnabled = true
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun finishActivity() {
        finish()
    }

    override fun clearFromEdt() {
        add_coin_from_edt.setText("")
    }
}
