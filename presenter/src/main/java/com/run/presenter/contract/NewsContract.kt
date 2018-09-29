package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleTitleModle

interface NewsContract {
    interface NewsView : BaseMvpView {
        fun callBackTitle(list: List<ArticleTitleModle.ArticleTitleBean>?)
    }

    class NewsPresenter(private val v: NewsView) : BaseMvpPresenter(v) {
        fun requestData() {
            addDisposable(ApiManager.article(), object : BaseObserver<ArticleTitleModle>() {
                override fun onSuccess(o: ArticleTitleModle) {
                    if (isViewAttached()) v.callBackTitle(o.data)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }
    }

}