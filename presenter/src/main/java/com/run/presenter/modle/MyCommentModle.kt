package com.run.presenter.modle


import com.run.config.modle.BaseModle

class MyCommentModle : BaseModle() {


    var data: List<MyCommentBean>? = null

    class MyCommentBean {
        lateinit var reply_msg: String//评论的内容
        lateinit var create_time: String//评论时间
        lateinit var title: String//段子标题
        var goodpost: Int = 0//好评数
        var badpost: Int = 0//被踩数
        var details_id: Int = 0//段子ID
    }
}
