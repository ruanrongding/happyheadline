package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.run.common.base.BaseActivity
import com.run.presenter.contract.ContentDetailContract
import com.run.presenter.modle.UserItemBean
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_content_detail.*

class ContentDetailActivity : BaseActivity<ContentDetailContract.ContentDetailPresenter>(), ContentDetailContract.ConteentDetailView {


    companion object {
        fun newInstance(activity: Context, title: String, id: Int) {
            val intent = Intent(activity, ContentDetailActivity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("id", id)
            activity.startActivity(intent)


        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_content_detail
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): ContentDetailContract.ContentDetailPresenter {
        return ContentDetailContract.ContentDetailPresenter(this)
    }
    override fun initData() {
        val title = intent.getStringExtra("TITLE")
        titleView.text = title

        val id = intent.getIntExtra("id", 0)
        mPresenter!!.requesetData(id)
    }
    override fun callBackData(data: UserItemBean) {
        webView.loadDataWithBaseURL(null, data.content, "text/html", "utf-8", null)

    }

}
