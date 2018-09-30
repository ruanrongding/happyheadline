package com.run.ui.fragment

import android.view.View
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.ApprenticeContract
import com.run.presenter.modle.ApprenticeBean
import com.run.ui.R
import com.run.ui.adapter.ApprenticeAdapter

/**
 * 徒弟列表页面
 */
class ApprenticeFragment : BaseListFragment<ApprenticeContract.ApprenticePresenter, ApprenticeBean>(), ApprenticeContract.ApprenticeView {
    companion object {
        fun newInstance(): ApprenticeFragment {
            return ApprenticeFragment()
        }
    }


    override fun initPresenter(): ApprenticeContract.ApprenticePresenter? {
        return ApprenticeContract.ApprenticePresenter(this)
    }

    private lateinit var adapter: ApprenticeAdapter
    private var headView: View? = null
    override fun initData() {
        adapter = ApprenticeAdapter()
        initAdapter(adapter)
        if (headView == null) {
            headView = View.inflate(activity, R.layout.header_apprentice_layout, null)
            if (adapter != null) {
                adapter.addHeaderView(headView)
            }
        }
        requestData()
    }
    override fun requestData() {
        mPresenter!!.invite_list(mPage)
    }

    override fun showData(list: List<ApprenticeBean>?) {
        setData(list)
    }

}