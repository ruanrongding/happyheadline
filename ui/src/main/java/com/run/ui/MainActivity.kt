package com.run.ui

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import com.run.common.base.BaseActivity
import com.run.common.utils.UActivityManager
import com.run.common.utils.UStatusBar
import com.run.common.view.NoScrollViewPager
import com.run.presenter.contract.MainContract
import com.run.ui.fragment.HomeFragment

class MainActivity : BaseActivity<MainContract.MainPresenter>(), MainContract.MainView {
    companion object {
        val TAG: String = MainActivity.javaClass.name
        fun newInstance(context: Activity) {
            context.startActivity(Intent(context, MainActivity::class.java))
            context.finish()
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    private lateinit var indexView: TextView
    private lateinit var communityView: TextView
    private lateinit var vedioView: TextView
    private lateinit var persionView: TextView
    private lateinit var viewpager: NoScrollViewPager
    override fun initViews() {
        //设置状态栏
        UStatusBar.setTransparentForImageViewInFragment(this@MainActivity, null)
        viewpager = findViewById(R.id.viewpager)
        indexView = findViewById(R.id.indexView)
        communityView = findViewById(R.id.communityView)
        persionView = findViewById(R.id.persionView)
        vedioView = findViewById(R.id.vedioView)
        indexView.setOnClickListener { setStatus(0) }
        communityView.setOnClickListener { setStatus(1) }
        vedioView.setOnClickListener { setStatus(2) }
        persionView.setOnClickListener {
            //            if (LoginHelper.instance.isLogin(this)) {
            setStatus(3)
//            }
        }
    }

    /**
     * 初始化底部导航栏状态
     */
    private fun setStatus(position: Int) {
        initStatus()
        when (position) {
            0 -> indexView.isSelected = true
            1 -> communityView.isSelected = true
            2 -> vedioView.isSelected = true
            3 -> persionView.isSelected = true
        }
        viewpager.currentItem = position
    }

    /**
     * 初始化状态
     */
    private fun initStatus() {
        indexView.isSelected = false
        communityView.isSelected = false
        vedioView.isSelected = false
        persionView.isSelected = false
    }

    override fun initPresenter(): MainContract.MainPresenter? {
        return MainContract.MainPresenter(this)
    }
    //============================================================初始化数据=========================
    /**
     * 首页的fragment集合
     */
    lateinit var fragmentList: List<Fragment>

    override fun initData() {
        fragmentList = arrayListOf(HomeFragment.newInstance(),
                HomeFragment.newInstance(),
                HomeFragment.newInstance(),
                HomeFragment.newInstance())
        viewpager.adapter = MainFragmentAdapter(supportFragmentManager)
        viewpager.offscreenPageLimit = 4
        setStatus(0)

        mPresenter!!.statisticsActive(this@MainActivity
        )
    }
    //==============================================fragment集合====================================
    inner class MainFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }

    //======================================= 退出应用 ==============================================
    private var mIsExit: Boolean = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                UActivityManager.exit()
            } else {
                Toast.makeText(this, "再按一次退出" + getString(R.string.app_name),
                        Toast.LENGTH_SHORT).show()
                mIsExit = true
                Handler().postDelayed({ mIsExit = false }, 2000)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
