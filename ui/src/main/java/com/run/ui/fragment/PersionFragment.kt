package com.run.ui.fragment

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseFragment
import com.run.common.utils.UGlide
import com.run.common.utils.UStatusBar
import com.run.presenter.contract.PersionContract
import com.run.presenter.modle.UserModle
import com.run.ui.R
import com.run.ui.activity.SettingActivity

/**
 * 个人中心
 */
class PersionFragment : BaseFragment<PersionContract.PersionPresenter>(), PersionContract.PersionView {


    companion object {
        fun newInstance(): PersionFragment {
            return PersionFragment()
        }
    }
    override fun initContentView(): Int {
        return R.layout.fragment_persion
    }

    private lateinit var headerView: ImageView
    private lateinit var sexView: ImageView
    private lateinit var nickView: TextView
    private lateinit var signerView: TextView
    private lateinit var apprenticeView: TextView
    private lateinit var moneyView: TextView
    private lateinit var coinView: TextView

    override fun initView(view: View) {
        headerView = view.findViewById(R.id.headerView)
        sexView = view.findViewById(R.id.sexView)
        nickView = view.findViewById(R.id.nickView)
        signerView = view.findViewById(R.id.signerView)
        apprenticeView = view.findViewById(R.id.apprenticeView)
        moneyView = view.findViewById(R.id.moneyView)
        coinView = view.findViewById(R.id.coinView)

        view.findViewById<View>(R.id.settingView).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.settingView -> SettingActivity.newInstance(activity!!)
        }
    }


    override fun initPresenter(): PersionContract.PersionPresenter? {
        return PersionContract.PersionPresenter(this)
    }

    override fun initData() {}
    override fun visiable() {
        UStatusBar.setDarkMode(this!!.activity!!)
        mPresenter!!.requestDate()
    }

    override fun callBackData(modle: UserModle) {
        val data: UserModle.DataBean = modle.data!!
        UGlide.loadCircleImage(activity, data.head_avatar, headerView)
        nickView.text = data.nick_name
        signerView.text = if (TextUtils.isEmpty(data.idiograph)) "这家伙很懒，什么都没写！" else data.idiograph
        apprenticeView.text = data.gold_balance.toString()
        moneyView.text = data.profit_balance
        apprenticeView.text = if (TextUtils.isEmpty(data.profit_total)) "0" else data.profit_total
    }

}