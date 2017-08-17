package com.rmnivnv.cryptomoon.view.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.rmnivnv.cryptomoon.R
import com.rmnivnv.cryptomoon.utils.app
import javax.inject.Inject
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.view.coins.CoinsFragment
import com.rmnivnv.cryptomoon.view.ico.IcoFragment
import com.rmnivnv.cryptomoon.view.fragments.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IMain.View {

    val component by lazy { app.component.plus(MainModule(this)) }

    @Inject lateinit var presenter: IMain.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    private lateinit var coinsLoading: ProgressBar
    private var deleteMenuItem: MenuItem? = null
    private var addMenuItem: MenuItem? = null
    private var settingsMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        presenter.onCreate(component)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CoinsFragment(), resProvider.getString(R.string.coins))
        adapter.addFragment(IcoFragment(), resProvider.getString(R.string.ico))
        adapter.addFragment(NewsFragment(), resProvider.getString(R.string.news))
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
        setCustomTab()
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                presenter.onPageSelected(position)
            }
        })
    }

    private fun setCustomTab() {
        val customTab = LayoutInflater.from(this).inflate(R.layout.tab_with_loading, null) as RelativeLayout
        val title = customTab.findViewById(R.id.tab_title) as TextView
        coinsLoading = customTab.findViewById(R.id.tab_loading) as ProgressBar
        title.text = resProvider.getString(R.string.coins)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    title.setTextColor(resProvider.getColor(R.color.colorAccent))
                } else {
                    title.setTextColor(resProvider.getColor(R.color.grey_light))
                }
            }

        })
        tabs.getTabAt(0)?.customView = customTab
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        deleteMenuItem = menu?.findItem(R.id.main_menu_delete)
        addMenuItem = menu?.findItem(R.id.main_menu_add_coin)
        settingsMenuItem = menu?.findItem(R.id.main_menu_settings)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.main_menu_add_coin -> presenter.onAddCoinClicked()
            R.id.main_menu_settings -> presenter.onSettingsClicked()
            R.id.main_menu_delete -> presenter.onDeleteClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setMenuIconsVisibility(isSelected: Boolean) {
        deleteMenuItem?.isVisible = isSelected
        addMenuItem?.isVisible = !isSelected
        settingsMenuItem?.isVisible = !isSelected
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

    override fun setCoinsLoadingVisibility(isLoading: Boolean) {
        if (isLoading) coinsLoading.visibility = View.VISIBLE
        else coinsLoading.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun startActivityFromIntent(intent: Intent) {
        startActivity(intent)
    }
}
