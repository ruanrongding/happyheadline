package com.run.ui.fragment

import android.os.Bundle
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.ArticleContract
import com.run.presenter.modle.ArticleBean
import com.run.ui.adapter.ArticleAdapter

class ArticleFragment : BaseListFragment<ArticleContract.ArticlePresenter, ArticleBean>(), ArticleContract.ArticleView {
    companion object {
        fun newInstance(type: String): ArticleFragment {
            var fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putString("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(category_id: Int): ArticleFragment {
            val fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putInt("CATEGORY_ID", category_id)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(type: String, userid: Int): ArticleFragment {
            val fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putString("TYPE", type)
            bundle.putInt("USERID", userid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initPresenter(): ArticleContract.ArticlePresenter? {
        return ArticleContract.ArticlePresenter(this)
    }

    private var mType: String? = ""
    private var CATEGORY_ID = -1
    private var mUserid: Int = 0
    private lateinit var adapter: ArticleAdapter

    override fun initData() {
        mType = arguments!!.getString("TYPE")
        CATEGORY_ID = arguments!!.getInt("CATEGORY_ID")
        mUserid = arguments!!.getInt("USERID")
        adapter = ArticleAdapter()
        initAdapter(adapter)
        if ("collect_list" == mType) {
            requestData()
        }
    }

    override fun visiable() {
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData(mType, mPage, mUserid, CATEGORY_ID)
    }

    override fun callBackData(list: List<ArticleBean>?) {
        for (bean: ArticleBean in list!!) {
            when (bean.category_id) {
                1 -> bean.itemType = ArticleBean.ARTICLE_TEXT
                2 -> {
                    if (bean.litpic!!.endsWith(".gif")) {
                        if (bean.litpic_height > bean.litpic_width) bean.itemType = ArticleBean.ARTICLE_GIF_PLUS else bean.itemType = ArticleBean.ARTICLE_GIFT
                    } else if (bean.litpic!!.endsWith(".png") || bean.litpic!!.endsWith(".jpg")) {
                        if (bean.litpic_height > 1000) bean.itemType = ArticleBean.ARTICLE_IMAGE_PLUS else bean.itemType = ArticleBean.ARTICLE_IMAGE
                    } else {
                        bean.itemType = ArticleBean.ARTICLE_IMAGE_PLUS
                    }
                }
                3 -> bean.itemType = ArticleBean.ARTICLE_VEDIO
                4 -> if (bean.litpic!!.endsWith(".gif")) {
                    if (bean.litpic_height > bean.litpic_width) bean.itemType = ArticleBean.ARTICLE_GIF_PLUS else bean.itemType = ArticleBean.ARTICLE_GIFT
                } else {
                    bean.itemType = ArticleBean.ARTICLE_IMAGE_PLUS
                }
            }
        }
        setData(list)
    }
}