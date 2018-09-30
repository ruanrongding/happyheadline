package com.run.presenter.modle


import com.run.config.modle.BaseModle

class MsgTypeModle : BaseModle() {


    /**
     * status : 200
     * data : [{"msg_id":1,"title":"官方消息","msg_type":2,"ico_url":"","new_mes":{"content":"","create_time":"2018-03-26 18:17:41"},"my_msg_mun":0},{"msg_id":2,"title":"通知提醒","msg_type":1,"ico_url":"","new_mes":{"content":"","create_time":"2018-03-26 18:17:50"},"my_msg_mun":1},{"msg_id":3,"title":"评论回复","msg_type":1,"ico_url":"","new_mes":null,"my_msg_mun":0},{"msg_id":4,"title":"私信","msg_type":1,"ico_url":"","new_mes":null,"my_msg_mun":0}]
     */

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * msg_id : 1
         * title : 官方消息
         * msg_type : 2
         * ico_url :
         * new_mes : {"content":"","create_time":"2018-03-26 18:17:41"}
         * my_msg_mun : 0
         */

        var msg_id: Int = 0
        var title: String? = null
        var msg_type: Int = 0
        var ico_url: String? = null
        var new_mes: NewMesBean? = null
        var my_msg_mun: Int = 0

        class NewMesBean {
            /**
             * content :
             * create_time : 2018-03-26 18:17:41
             */

            var content: String? = null
            var create_time: String? = null
        }
    }
}
