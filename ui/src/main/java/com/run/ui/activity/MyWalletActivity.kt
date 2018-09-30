package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import com.run.common.base.BaseActivity
import com.run.common.utils.UStatusBar
import com.run.presenter.contract.WalletContract
import com.run.presenter.modle.WalletModle
import com.run.tablayout.TabLayout
import com.run.ui.R
import com.run.ui.fragment.ArticleFragment
import com.run.ui.fragment.RevenueDetailFragment
import kotlinx.android.synthetic.main.activity_my_wallet.*

/**
 * 我的钱包
 */
class MyWalletActivity : BaseActivity<WalletContract.WalletPresenter>(), WalletContract.WalletView {


    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, MyWalletActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_my_wallet
    }

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun initViews() {
        UStatusBar.setDarkMode(this)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        backView.setOnClickListener { finish() }
        recordView.setOnClickListener { WithDrawBillActivity.newInstance(this) }
    }

    override fun initPresenter(): WalletContract.WalletPresenter {
        return WalletContract.WalletPresenter(this)
    }

    override fun initData() {
        mPresenter!!.requesetData()

        val adapter = WalletPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    @SuppressLint("SetTextI18n")
    override fun callbackData(modle: WalletModle?) {
        moenyView.text = if (TextUtils.isEmpty(modle!!.profit_balance)) "0.0" else modle!!.profit_balance
        hintView.text = "乐币转换汇率受每日运营收益影响\n" + modle!!.text + "\n 乐币每满1000将自动兑换为零钱(上下会有浮动)"
    }

    //================================adapter===========================================================
    private inner class WalletPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var mTilte: Array<String> = resources.getStringArray(R.array.tab_wallet_title)
        private var fragment: Fragment? = null
        override fun getPageTitle(position: Int): CharSequence? {
            return mTilte[position]
        }

        override fun getItem(position: Int): Fragment? {
            fragment = when (position) {
                0 -> RevenueDetailFragment.newInstance(0)
                1 -> RevenueDetailFragment.newInstance(7)
                else -> RevenueDetailFragment.newInstance(0)
            }
            return fragment
        }

        override fun getCount(): Int {
            return mTilte.size
        }
    }

}
