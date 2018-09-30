package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.MsgTypeModle

interface MessageContract {
    interface MessageView : BaseMvpView {
        fun callBackData(list: List<MsgTypeModle.DataBean>?)
    }

    class MessagePresenter(private val v: MessageView) : BaseMvpPresenter(v) {
        fun requestDeta() {
            addDisposable(ApiManager.message_list(), object : BaseObserver<MsgTypeModle>() {
                override fun onSuccess(o: MsgTypeModle) {
                    if (isViewAttached()) v.callBackData(o.data)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }
    }
}