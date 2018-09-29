package com.run.presenter.modle

import com.run.config.modle.BaseModle

class CircleDetailModle : BaseModle() {

    /**
     * status : 200
     * data : {"circle_id":1,"circle_name":"热门","circle_head":"http://img3.duitang.com/uploads/item/201502/10/20150210153138_xVVdH.jpeg","circle_background":"http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/01/40/b693a4f8fc5ccde4c07207e8db5d64ac.jpg","intro":"啦啦啦啦啦"}
     * attention_type : 0
     */

    var data: DataBean? = null
    var attention_type: Int = 0
    class DataBean {
        /**
         * circle_id : 1
         * circle_name : 热门
         * circle_head : http://img3.duitang.com/uploads/item/201502/10/20150210153138_xVVdH.jpeg
         * circle_background : http://pic.90sjimg.com/back_pic/qk/back_origin_pic/00/01/40/b693a4f8fc5ccde4c07207e8db5d64ac.jpg
         * intro : 啦啦啦啦啦
         */

        var circle_id: Int = 0
        var circle_name: String? = null
        var circle_head: String? = null
        var circle_background: String? = null
        var intro: String? = null
    }
}
