package com.rmnivnv.cryptomoon.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.utils.app
import javax.inject.Inject
import android.support.v4.app.FragmentPagerAdapter
import com.rmnivnv.cryptomoon.view.coins.CoinsFragment
import com.rmnivnv.cryptomoon.view.ico.IcoFragment
import com.rmnivnv.cryptomoon.view.fragments.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainInterface.View {

    val component by lazy { app.component.plus(MainModule(this)) }
    @Inject lateinit var presenter: MainInterface.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setupViewPager()
        presenter.onCreate()
    }

    fun getAppComponent() = component

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CoinsFragment(), getString(R.string.coins))
        adapter.addFragment(IcoFragment(), getString(R.string.ico))
        adapter.addFragment(NewsFragment(), getString(R.string.news))
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int) = mFragmentList[position]

        override fun getCount() = mFragmentList.size

        override fun getPageTitle(position: Int) = mFragmentTitleList[position]

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }
}
