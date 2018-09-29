package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.CircleModle
import io.reactivex.Observable

interface CircleContract {
    interface CircleView : BaseMvpView {
        fun callBackCircle(list: List<CircleModle.DataBean>?)
    }

    class CirclePresenter(private val v: CircleView) : BaseMvpPresenter(v) {
        fun requestData(type: Int) {
            var observable: Observable<CircleModle>? = null
            when (type) {
                0 -> observable = ApiManager.circle_list()
                1 -> observable = ApiManager.circle_attention_list()
            }
            addDisposable(observable!!, object : BaseObserver<CircleModle>() {
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

                override fun onSuccess(o: CircleModle) {
                    if (isViewAttached()) v.callBackCircle(o.data)
                }
            })

        }
    }
}