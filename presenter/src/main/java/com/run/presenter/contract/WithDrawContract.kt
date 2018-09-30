package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.IncomeResultModle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 提现
 */
interface WithDrawContract {

    interface WithDrawView : BaseMvpView {
        fun showData(modle: IncomeModle)
        fun gotoBindWC(msg: String, url: String)
        fun moneyFinish(msg: String)
        fun showMoneyError(msg: String)
    }

    class WithDrawPresenter(private val v: WithDrawView) : BaseMvpPresenter(v) {
        fun money_view() {
            if (isViewAttached()) v.showLoading()

            addDisposable(ApiManager.money_view(), object : BaseObserver<IncomeModle>() {
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.showErr(errorType, msg!!)
                    }
                }

                override fun onSuccess(o: IncomeModle) {
                    if (isViewAttached()) {
                        v.showData(o)
                        v.hideLoading()
                    }

                }
            })

        }

        fun money(money: Int, type: Int, my_voucherid: Int) {
            if (isViewAttached()) v.showLoading()
            ApiManager.money(money, type, my_voucherid).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<IncomeResultModle> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(bean: IncomeResultModle) {
                            if (isViewAttached()) v.hideLoading()
                            if (bean == null) return
                            if (bean.status == -100) {
                                when (bean.code) {
                                    10045 -> v.gotoBindWC(bean!!.msg!!, bean.url!!)
                                    else -> v.showMoneyError(bean!!.msg!!)
                                }
                                return
                            }
                            if (bean.status == 200) {
                                v.moneyFinish(bean!!.msg!!)
                            }
                        }

                        override fun onError(e: Throwable) {
                            v.showMsg("提现失败")
                        }

                        override fun onComplete() {
                        }
                    })
        }

    }
}
