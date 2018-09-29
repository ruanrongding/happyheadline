package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleDetailModle

/**
 * 文章详情页面
 */
interface ArticleDetailContract {

    interface ArticleDetailView : BaseMvpView {
        fun callBackData(bean: ArticleDetailModle.DataBean)
    }

    class ArticleDetailPresenter(private val v: ArticleDetailView) : BaseMvpPresenter(v) {
        fun requestData(mArticleid: Int) {
            if (isViewAttached()) {
                v.showLoading()
            }

            addDisposable(ApiManager.articledetail(mArticleid), object : BaseObserver<ArticleDetailModle>() {
                override fun onSuccess(o: ArticleDetailModle) {
                    if (isViewAttached()) {
                        v.callBackData(o.data!!)
                        v.hideLoading()
                    }

                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.showErr(errorType, msg!!)
                        v.hideLoading()
                    }
                }

            })
        }


    }
}