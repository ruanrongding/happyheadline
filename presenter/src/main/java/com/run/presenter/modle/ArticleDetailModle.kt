package com.run.presenter.modle


import com.run.config.modle.BaseModle

class ArticleDetailModle : BaseModle() {


    var data: DataBean? = null
    var list: List<ArticleBean>? = null

    var attention_type: Int = 0


    class DataBean {
        /**
         * category_id : 3
         * title : 赶了一天的庙会，回来就成这个样子了……
         * content : http://mvideo.spriteapp.cn/video/2018/0113/5a59d38f04668_wpc.mp4
         * litpic : http://wimg.spriteapp.cn/picture/2018/0113/5a59d38eecca9__b.jpg
         * publish_begin : 2018-03-19 11:27:01
         * content_type : 2
         */

        var category_id: Int = 0
        var title: String? = null
        var content: String? = null
        var litpic: String? = null
        var publish_begin: String? = null
        var content_type: Int = 0
        var user_id: Int = 0
        var video_url: String? = null
        var details_type: Int = 0

        var litpic_height: Int = 0
        var litpic_width: Int = 0

        var is_width_height: Int = 0
    }
}
