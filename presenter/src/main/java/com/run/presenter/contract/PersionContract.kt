package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.UserModle

interface PersionContract {
    interface PersionView : BaseMvpView {
        fun callBackData(modle: UserModle)
    }

    class PersionPresenter(private val v: PersionView) : BaseMvpPresenter(v) {
        fun requestDate() {
            addDisposable(ApiManager.index(), object : BaseObserver<UserModle>() {
                override fun onSuccess(o: UserModle) {
                    if (isViewAttached()) v.callBackData(o)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }
    }

}