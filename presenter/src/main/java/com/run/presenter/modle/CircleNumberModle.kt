package com.run.presenter.modle

import com.run.config.modle.BaseModle

class CircleNumberModle : BaseModle() {

    /**
     * status : 200
     * data : {"user_id":"ID","nick_name":"名称","head_avatar":"头像"}
     */


    var data: List<DataBean>? = null


    class DataBean {
        /**
         * user_id : ID
         * nick_name : 名称
         * head_avatar : 头像
         */

        var user_id: Int = 0
        var nick_name: String? = null
        var head_avatar: String? = null
    }
}
