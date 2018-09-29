package com.run.presenter.modle

class CommentBean {
    // {"comment_id":2,"user_id":13774,"reply_msg":"地对地导弹","goodpost":0,"badpost":0,"nick_name":"淡定","head_avatar":null,"reply_data":[]
    var comment_id: Int = 0
    var user_id: Int = 0
    var reply_msg: String? = null
    var goodpost: Int = 0
    var badpost: String? = null
    var nick_name: String? = null
    var head_avatar: String? = null

    var gender: Int = 0

    var experience_grade: Int = 0

    var like_type: Int = 0

    var reply_data: List<ReplyBean>? = null//评论列表


}
