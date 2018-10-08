package com.run.ui.fragment

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseFragment
import com.run.common.utils.UGlide
import com.run.common.utils.UStatusBar
import com.run.conifg.AppIntentAction
import com.run.presenter.contract.PersionContract
import com.run.presenter.modle.UserModle
import com.run.ui.R
import com.run.ui.activity.*

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
    private lateinit var msgCountView: TextView
    private lateinit var inviteLayout: TextView

    override fun initView(view: View) {
        headerView = view.findViewById(R.id.headerView)
        sexView = view.findViewById(R.id.sexView)
        nickView = view.findViewById(R.id.nickView)
        signerView = view.findViewById(R.id.signerView)
        apprenticeView = view.findViewById(R.id.apprenticeView)
        moneyView = view.findViewById(R.id.moneyView)
        coinView = view.findViewById(R.id.coinView)
        msgCountView = view.findViewById(R.id.msgCountView)

        view.findViewById<View>(R.id.coinLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.moneyLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.apprenticeLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.settingView).setOnClickListener(this)
        view.findViewById<View>(R.id.qqView).setOnClickListener(this)
        view.findViewById<View>(R.id.feedBackLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.problemLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.collectLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.msgLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.walletLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.withdrawLayout).setOnClickListener(this)
        view.findViewById<View>(R.id.inviteLayout).setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.settingView -> SettingActivity.newInstance(activity!!)
            R.id.qqView -> AppIntentAction.joinQQGroup(mQQKey, activity!!)
            R.id.feedBackLayout -> FeedBackActivity.newInstance(activity!!)
            R.id.problemLayout -> ProblemActivity.newInstance(activity!!, 1)
            R.id.collectLayout -> CollectListActivity.newInstance(activity!!)
            R.id.msgLayout -> MessageActivity.newInstance(activity!!)
            R.id.walletLayout, R.id.coinLayout, R.id.moneyLayout -> MyWalletActivity.newInstance(activity!!)
            R.id.apprenticeLayout -> ApprenticeListActivity.newInstance(activity!!)
            R.id.withdrawLayout -> WithDrawActivity.newInstance(activity!!)
            R.id.inviteLayout -> InviteActivity.newInstance(activity!!)
        }
    }

    override fun initPresenter(): PersionContract.PersionPresenter? {
        return PersionContract.PersionPresenter(this)
    }

    override fun initData() {
        mPresenter!!.getQQKey()
    }

    override fun visiable() {
        UStatusBar.setDarkMode(this!!.activity!!)
        mPresenter!!.requestDate()
    }

    override fun callBackData(modle: UserModle) {
        val data: UserModle.DataBean = modle.data!!
        UGlide.loadCircleImage(activity, data.head_avatar, headerView)
        nickView.text = data.nick_name + "(ID:" + data.user_id + ")"
        signerView.text = if (TextUtils.isEmpty(data.idiograph)) "这家伙很懒，什么都没写！" else data.idiograph
        coinView.text = data.gold_balance.toString()
        moneyView.text = data.profit_balance
        apprenticeView.text = modle.invite_mun.toString()
        if (modle.my_msg_mun <= 0) {
            msgCountView.visibility = View.GONE
        } else {
            msgCountView.visibility = View.VISIBLE
            msgCountView.text = if (modle.my_msg_mun < 100) modle.my_msg_mun.toString() else "99+"
        }
    }

    /**
     * 加入QQ群的key
     */
    private lateinit var mQQKey: String


    override fun callBackQQKey(qqKey: String) {
        mQQKey = qqKey
    }


}