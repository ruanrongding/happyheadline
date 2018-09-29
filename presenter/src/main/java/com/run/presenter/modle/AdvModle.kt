package com.run.presenter.modle

import com.run.config.modle.BaseModle

class AdvModle : BaseModle() {

    /**
     * status : 200
     * type : 1
     * data : [{"advert_id":"574","advert_place":17,"advert_type":1,"advert_img":"https://wx1.sinaimg.cn/mw1024/a14e9a45gy1fqdf3oeqj4g20hs03caaj.gif","advert_title":"","name":"","advert_sort":0,"advert_desc":"","mode":1}]
     */

    var type: Int = 0  //0不显示广告 1，显示广告
    var data: List<DataBean>? = null

    class DataBean {
        /**
         * advert_id : 574
         * advert_place : 17
         * advert_type : 1
         * advert_img : https://wx1.sinaimg.cn/mw1024/a14e9a45gy1fqdf3oeqj4g20hs03caaj.gif
         * advert_title :
         * name :
         * advert_sort : 0
         * advert_desc :
         * mode : 1
         */

        var advert_id: String? = null
        var advert_place: Int = 0
        var advert_type: Int = 0
        var advert_img: String? = null
        var advert_title: String? = null
        var name: String? = null
        var advert_sort: Int = 0
        var advert_desc: String? = null
        var mode: Int = 0
    }
}
