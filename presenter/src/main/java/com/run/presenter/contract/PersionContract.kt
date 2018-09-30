package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.UserModle
import com.run.presenter.modle.login.QQModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PersionContract {
    interface PersionView : BaseMvpView {
        fun callBackData(modle: UserModle)
        fun callBackQQKey(qqKey: String)
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

        /*
       *获取到QQ群的key
       */
        fun getQQKey() {
            LoginManager.getQQKey().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<QQModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            if (isViewAttached()) v.showErr(errorType, msg!!)
                        }
                        override fun onSuccess(o: QQModle) {
                            if (isViewAttached()) v.callBackQQKey(o.key!!)
                        }

                    })
        }
    }

}