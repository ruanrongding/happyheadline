package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.MsgModle

interface MsgListContract {
    interface MsgListView : BaseMvpView {
        fun callBackData(list: List<MsgModle.DataBean>?)
    }

    class MsgListPresenter(private val v: MsgListView) : BaseMvpPresenter(v) {
        fun requeestData(msgId: Int) {
            addDisposable(ApiManager.my_msg(msgId), object : BaseObserver<MsgModle>() {
                override fun onSuccess(o: MsgModle) {
                    if (isViewAttached()) v.callBackData(o.data)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }
    }
}