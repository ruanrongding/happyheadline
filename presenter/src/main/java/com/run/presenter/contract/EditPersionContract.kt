package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.UserInfoModile

interface EditPersionContract {
    interface EditPersionView : BaseMvpView {
        fun callBackData(data: UserInfoModile.DataBean)
    }

    class EditPresionPresenter(private val v: EditPersionView) : BaseMvpPresenter(v) {

        fun getUserInfo() {
            if (isViewAttached()) v.showLoading()
            addDisposable(ApiManager.user_info(), object : BaseObserver<UserInfoModile>() {
                override fun onSuccess(o: UserInfoModile) {
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