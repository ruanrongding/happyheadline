package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.TaskModle

interface TaskContract {
    interface TaskView : BaseMvpView {
        fun callBackData(modle: TaskModle)
        fun callBackSign(msg: String)
        fun callBackError(msg: String)
    }

    class TaskPresenter(private val v: TaskView) : BaseMvpPresenter(v) {
        fun requestData() {
            addDisposable(ApiManager.task_list(), object : BaseObserver<TaskModle>() {
                override fun onSuccess(o: TaskModle) {
                    if (isViewAttached()) v.callBackData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })

        }

        fun sign() {
            addDisposable(LoginManager.sign(), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) v.callBackSign(o.msg!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.callBackError( msg!!)
                }

            })
        }
    }
}