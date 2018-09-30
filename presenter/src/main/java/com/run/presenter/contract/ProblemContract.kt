package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.DynamicsModle
import com.run.presenter.modle.UserItemBean

/**
 * 常见问题
 */
interface ProblemContract {

    interface ProblemView : BaseMvpView {
        fun showData(list: List<UserItemBean>)
    }

    class ProblemPresenter(private val v: ProblemView) : BaseMvpPresenter(v) {
        fun dynamics(type: Int) {
            addDisposable(ApiManager.dynamics(type), object : BaseObserver<DynamicsModle>() {
                override fun onSuccess(o: DynamicsModle) {
                    if (isViewAttached()) v.showData(o.data!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }

    }
}
