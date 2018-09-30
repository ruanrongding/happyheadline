package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.WalletModle

interface WalletContract {

    interface WalletView : BaseMvpView {
        fun callbackData(modle: WalletModle?)
    }

    class WalletPresenter(private val v: WalletView) : BaseMvpPresenter(v) {
        fun requesetData() {
            addDisposable(ApiManager.my_wallet(), object : BaseObserver<WalletModle>() {
                override fun onSuccess(o: WalletModle) {
                    if (isViewAttached()) v.callbackData(o)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }
    }
}