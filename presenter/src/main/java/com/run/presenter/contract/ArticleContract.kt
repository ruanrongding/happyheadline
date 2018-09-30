package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.ArticleModle
import io.reactivex.Observable

interface ArticleContract {

    interface ArticleView : BaseMvpView {
        fun callBackData(list: List<ArticleBean>?)
    }

    class ArticlePresenter(private val v: ArticleView) : BaseMvpPresenter(v) {
        fun requestData(type: String?, mPage: Int, mUserid: Int, CATEGORY_ID: Int) {
            val observable: Observable<ArticleModle>? = when (type) {
                "news" -> ApiManager.articleessence(mPage)
                "day" -> ApiManager.recommend_day(mPage)
                "week", "month" -> ApiManager.seniority_details(type, mPage)
                "discover_newes" -> ApiManager.discover_newest(mPage)
                "discover_hot" -> ApiManager.discover_hot(mPage)
                "my_article" -> ApiManager.my_article(mUserid, mPage)
                "ym_share" -> ApiManager.ym_share(mUserid, mPage)
                "circle_hot" -> ApiManager.circle_article(mUserid, "hot", mPage)
                "circle_news" -> ApiManager.circle_article(mUserid, "news", mPage)
                "collect_list" -> ApiManager.collect_list(mPage)
                else -> ApiManager.articlelist(CATEGORY_ID, mPage)
            }
            addDisposable(observable!!, object : BaseObserver<ArticleModle>() {
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)

                }

                override fun onSuccess(o: ArticleModle) {
                    if (isViewAttached()) v.callBackData(o.data)

                }
            })

        }


    }
}