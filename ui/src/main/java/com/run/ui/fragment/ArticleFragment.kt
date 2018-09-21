package com.run.ui.fragment

import android.os.Bundle
import android.view.View
import com.run.common.base.BaseFragment
import com.run.ui.R

class ArticleFragment : BaseFragment<Nothing>() {

    companion object {
        fun newInstance(type: String): ArticleFragment {
            var fragment = ArticleFragment()
            var bundle = Bundle()
            bundle.putString("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initContentView(): Int {
        return R.layout.fragment_article
    }

    override fun initView(view: View) {
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
    }

}