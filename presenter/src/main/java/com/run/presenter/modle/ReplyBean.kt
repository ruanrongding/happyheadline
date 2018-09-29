package com.run.presenter.modle

/**
 * 回复内容
 */
class ReplyBean {
    //    {"comment_id":2,"from_user_id":13774,"from_user_name":"淡定","to_user_id":13774,"to_user_name":"淡定","reply_msg":"@13774:GG方法"}

    var comment_id: Int = 0
    var from_user_id: Int = 0
    var from_user_name: String? = null
    var to_user_id: Int = 0
    var to_user_name: String? = null
    var reply_msg: String? = null
}
