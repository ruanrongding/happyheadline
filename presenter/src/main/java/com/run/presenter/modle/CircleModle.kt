package com.run.presenter.modle

import com.run.config.modle.BaseModle

class CircleModle : BaseModle() {


    /**
     * status : 200
     * data : [{"circle_id":1,"circle_name":"热门","circle_head":"http://img3.duitang.com/uploads/item/201502/10/20150210153138_xVVdH.jpeg","intro":"","mun":0}]
     */

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * circle_id : 1
         * circle_name : 热门
         * circle_head : http://img3.duitang.com/uploads/item/201502/10/20150210153138_xVVdH.jpeg
         * intro :
         * mun : 0
         */

        var circle_id: Int = 0
        var circle_name: String? = null
        var circle_head: String? = null
        var intro: String? = null
        var mun: Int = 0
    }
}
