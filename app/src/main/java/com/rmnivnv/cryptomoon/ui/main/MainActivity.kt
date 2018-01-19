package com.rmnivnv.cryptomoon.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.rmnivnv.cryptomoon.R
import javax.inject.Inject
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.crashlytics.android.Crashlytics
import com.rmnivnv.cryptomoon.base.BaseActivity
import com.rmnivnv.cryptomoon.ui.addCoin.AddCoinActivity
import com.rmnivnv.cryptomoon.utils.ResourceProvider
import com.rmnivnv.cryptomoon.ui.coins.CoinsFragment
import com.rmnivnv.cryptomoon.ui.news.NewsFragment
import com.rmnivnv.cryptomoon.ui.settings.SettingsActivity
import com.rmnivnv.cryptomoon.ui.topCoins.TopCoinsFragment
import com.rmnivnv.cryptomoon.utils.toastShort
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), IMain.View {

    @Inject lateinit var presenter: IMain.Presenter
    @Inject lateinit var resProvider: ResourceProvider

    private lateinit var coinsLoading: ProgressBar
    private var deleteMenuItem: MenuItem? = null
    private var addMenuItem: MenuItem? = null
    private var sortMenuItem: MenuItem? = null
    private var settingsMenuItem: MenuItem? = null
    private lateinit var newsFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this, Crashlytics())
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupViewPager()
        presenter.onCreate()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(CoinsFragment(), resProvider.getString(R.string.coins))
        adapter.addFragment(TopCoinsFragment(), resProvider.getString(R.string.top100))
        newsFragment = NewsFragment()
        adapter.addFragment(newsFragment, resProvider.getString(R.string.news))

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
        val title: TextView = customTab.findViewById(R.id.tab_title)
        coinsLoading = customTab.findViewById(R.id.tab_loading)
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
        sortMenuItem = menu?.findItem(R.id.main_menu_sort)
        settingsMenuItem = menu?.findItem(R.id.main_menu_settings)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.main_menu_add_coin -> presenter.onAddCoinClicked()
            R.id.main_menu_sort -> presenter.onSortClicked()
            R.id.main_menu_settings -> presenter.onSettingsClicked()
            R.id.main_menu_delete -> presenter.onDeleteClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setMenuIconsVisibility(isSelected: Boolean) {
        deleteMenuItem?.isVisible = isSelected
        addMenuItem?.isVisible = !isSelected
        settingsMenuItem?.isVisible = !isSelected
        sortMenuItem?.isVisible = !isSelected
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

    override fun startAddCoinActivity() {
        startActivity(Intent(this, AddCoinActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        newsFragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun showToast(text: String) {
        this.toastShort(text)
    }

    override fun setSortVisible(isVisible: Boolean) {
        sortMenuItem?.isVisible = isVisible
    }

    override fun showCoinsSortDialog(sort: String) {
        val dialog = SortDialog()
        val bundle = Bundle()
        bundle.putString("sort", sort)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "sortDialog")
    }

    override fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}
