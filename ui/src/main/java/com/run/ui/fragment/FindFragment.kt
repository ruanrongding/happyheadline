package com.run.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.run.common.base.BaseFragment
import com.run.common.utils.UStatusBar
import com.run.tablayout.TabLayout
import com.run.ui.R

class FindFragment : BaseFragment<Nothing>() {

    companion object {
        fun newInstance(): FindFragment {
            return FindFragment()
        }
    }

    override fun initContentView(): Int {
        return R.layout.fragment_home
    }

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun initView(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    private var lists = arrayOf(CircleFragment.newInstance(0), CircleFragment.newInstance(1))
    override fun initData() {
        val adapter = FindPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }


    override fun visiable() {
        super.visiable()
        UStatusBar.setLightMode(this!!.activity!!) // 设置状态栏字体颜色
    }

    private inner class FindPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var mTilte: Array<String> = resources.getStringArray(R.array.tab_circle_title)
        override fun getPageTitle(position: Int): CharSequence? {
            return mTilte[position]
        }

        override fun getItem(position: Int): Fragment {
            return lists[position]
        }

        override fun getCount(): Int {
            return mTilte.size
        }
    }

}