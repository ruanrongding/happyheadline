package com.run.presenter.modle


import com.run.config.modle.BaseModle

class TaskModle : BaseModle() {
    var signtype: Int = 0//签到状态 0 没签到  1 已签到
    var sign_degree: Int = 0//连续签到天数

    var data: List<TaskBean>? = null
}
