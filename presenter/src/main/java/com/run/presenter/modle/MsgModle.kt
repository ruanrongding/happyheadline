package com.run.presenter.modle


import com.run.config.modle.BaseModle

class MsgModle : BaseModle() {


    /**
     * status : 200
     * data : [{"title":"人才 回复了你的评论","content":"[good][允悲][允悲][吃瓜][吃瓜][吃瓜]","msg_type":0,"create_time":"2018-07-25 13:21:08"}]
     */

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * title : 人才 回复了你的评论
         * content : [good][允悲][允悲][吃瓜][吃瓜][吃瓜]
         * msg_type : 0
         * create_time : 2018-07-25 13:21:08
         */

        var title: String? = null
        var content: String? = null
        var msg_type: Int = 0
        var create_time: String? = null

        var relation_id: Int = 0
    }
}
