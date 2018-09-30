package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.DyContentModle
import com.run.presenter.modle.UserItemBean

interface ContentDetailContract {
    interface ConteentDetailView : BaseMvpView {
        fun callBackData(data: UserItemBean)
    }

    class ContentDetailPresenter(private val v: ConteentDetailView) : BaseMvpPresenter(v) {
        fun requesetData(id: Int) {
            if (isViewAttached()) v.showLoading()
            addDisposable(ApiManager.problemContent(id), object : BaseObserver<DyContentModle>() {
                override fun onSuccess(o: DyContentModle) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.callBackData(o.data!!)

                    }
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.showErr(errorType, msg!!)
                    }
                }

            })
        }
    }
}