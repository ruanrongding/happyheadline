package com.run.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseListFragment
import com.run.common.utils.UToastDialog
import com.run.presenter.contract.TaskContract
import com.run.presenter.modle.TaskBean
import com.run.presenter.modle.TaskModle
import com.run.ui.R
import com.run.ui.adapter.SignAdapter
import com.run.ui.adapter.TaskAdapter
import java.util.*

/**
 * 任务列表的fragment
 */
class TaskFragment : BaseListFragment<TaskContract.TaskPresenter, TaskBean>(), TaskContract.TaskView {


    companion object {
        fun newInstance(): TaskFragment {
            return TaskFragment()
        }
    }

    override fun initContentView(): Int {
        return R.layout.layout_recyclerview2
    }

    override fun initPresenter(): TaskContract.TaskPresenter? {
        return TaskContract.TaskPresenter(this)
    }

    private lateinit var headView: View
    private lateinit var adapter: TaskAdapter
    override fun initData() {
        adapter = TaskAdapter()
        initAdapter(adapter)
        initHeadView()
        requestData()
    }

    private lateinit var signAdapter: SignAdapter
    private var signList: ArrayList<Int> = arrayListOf(0, 1, 2, 3, 4, 5, 6)
    private lateinit var signDayView: TextView
    private lateinit var singView: ImageView
    private fun initHeadView() {
        headView = View.inflate(activity, R.layout.layout_task_header, null)
        adapter.addHeaderView(headView)
        signDayView = headView.findViewById(R.id.signDayView)
        singView = headView.findViewById(R.id.singView)
        val signRecyclerveiw: RecyclerView = headView.findViewById(R.id.singRecyclerView)
        signRecyclerveiw.layoutManager = GridLayoutManager(activity, 7)
        signAdapter = SignAdapter()
        signRecyclerveiw.adapter = signAdapter
        signAdapter.sign_degree = sign_degree
        signAdapter.setNewData(signList)
        singView.setOnClickListener {
            mPresenter!!.sign()
        }
    }

    override fun requestData() {
        mPresenter!!.requestData()
    }

    private var sign_degree: Int = 0
    override fun callBackData(modle: TaskModle) {
        singView.isEnabled = modle.signtype == 0
        setData(modle.data, false)
        sign_degree = modle.sign_degree
        signDayView.text = "已连续签到" + sign_degree + "天"
        signAdapter.sign_degree = sign_degree
        signAdapter.notifyDataSetChanged()
    }
    override fun callBackSign(msg: String) {
        UToastDialog.getToastDialog().isCanToast = true
        UToastDialog.getToastDialog().ToastShow(activity, "签到奖励", 20 + sign_degree * 5)
        singView.isEnabled = false
        sign_degree++
        signAdapter.sign_degree = sign_degree
        signAdapter.notifyDataSetChanged()
    }

    override fun callBackError(msg: String) {
        showMsg(msg)
    }
}