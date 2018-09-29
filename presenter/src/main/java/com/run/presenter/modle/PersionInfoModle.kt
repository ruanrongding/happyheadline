package com.run.presenter.modle


import com.run.config.modle.BaseModle

class PersionInfoModle : BaseModle() {

    var user: User? = null

    class User {
        var nick_name: String? = null//昵称
        var head_avatar: String? = null//头像
        var experience_grade: String? = null// 经验等级,
        var fans_mun: String? = null//粉丝数
        var attention_mun: String? = null//关注数
        var attention_type: Int = 0

        var background: String? = null
    }

}
