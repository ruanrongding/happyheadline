package com.run.presenter.contract.login


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 注册
 */
interface BindMobileContract {
    interface BindMobileView : BaseMvpView {
        fun callBackfinish(msg: String)
        fun callBackCode()
    }

    class BindMobilePresenter(private val v: BindMobileContract.BindMobileView) : BaseMvpPresenter(v) {
        /**
         * 注册
         * @param mobile
         * @param code
         * @param password
         */
        fun bindMobile(mobile: String, code: String) {
            LoginManager.binding_phone(mobile, code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onSuccess(o: BaseModle) {
                            if (isViewAttached()) v.callBackfinish(o.msg!!)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            if (isViewAttached()) v.showErr(errorType, msg!!)
                        }
                    })
        }

        /**
         * 获取验证码
         * @param mobile
         * @param type
         */
        fun getCode(mobile: String, type: Int) {
            LoginManager.getCode(mobile, type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onSuccess(o: BaseModle) {
                            if (isViewAttached()) v.callBackCode()
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            if (isViewAttached()) v.showErr(errorType, msg!!)
                        }
                    })

        }
    }
}
