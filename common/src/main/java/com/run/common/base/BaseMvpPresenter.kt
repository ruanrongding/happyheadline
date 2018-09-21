package com.run.common.base

import com.run.common.utils.UCompositeDisposable
import io.reactivex.Observable


open class BaseMvpPresenter {


    /**
     * 添加网络请求
     */
    protected fun addDisposable(observable: Observable<*>, observer: BaseObserver<*>) {
        UCompositeDisposable.getInstance().addDisposable(observable, observer)
    }

}
