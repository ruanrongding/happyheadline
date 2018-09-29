package com.run.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.run.common.base.BaseFragment
import com.run.common.utils.UStatusBar
import com.run.presenter.contract.NewsContract
import com.run.presenter.modle.ArticleTitleModle
import com.run.tablayout.TabLayout
import com.run.ui.R

class NewsFragment : BaseFragment<NewsContract.NewsPresenter>(), NewsContract.NewsView {
    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
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


    //==================================数据操作=====================================================
    override fun initPresenter(): NewsContract.NewsPresenter? {
        return NewsContract.NewsPresenter(this)
    }
    override fun initData() {
    }
    override fun visiable() {
        super.visiable()
        UStatusBar.setLightMode(this!!.activity!!) // 设置状态栏字体颜色
        if (mList == null || mList!!.isEmpty()) {
            mPresenter!!.requestData()
        }
    }
    override fun callBackTitle(list: List<ArticleTitleModle.ArticleTitleBean>?) {
        if (mList == null) {
            this.mList = list
        }
        val adapter = NewsPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
    private var mList: List<ArticleTitleModle.ArticleTitleBean>? = null
    private inner class NewsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getPageTitle(position: Int): CharSequence? {
            return mList!![position].name
        }
        override fun getItem(position: Int): Fragment {
            return ArticleFragment.newInstance(mList!![position].category_id)
        }
        override fun getCount(): Int {
            return mList!!.size
        }
    }

}