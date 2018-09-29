package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager

interface ModifyNumberContract {
    interface ModifyNumberView : BaseMvpView {
        fun callBackFinish(msg: String)
    }

    class ModifyNumberPresenter(private val v: ModifyNumberView) : BaseMvpPresenter(v) {
        fun modifyNick(nick: String) {
            if (isViewAttached()) v.showLoading()
            addDisposable(LoginManager.user_save(nick, "", "", ""), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.callBackFinish("修改昵称成功")
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

        fun modifySigner(signer: String) {
            if (isViewAttached()) v.showLoading()
            addDisposable(LoginManager.user_save("", "", "", signer), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.callBackFinish("修改签名成功")
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

        fun modifyTeacher(teacher: String) {
            if (isViewAttached()) v.showLoading()
            addDisposable(LoginManager.bound_teacher(teacher), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.callBackFinish("拜师成功")
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