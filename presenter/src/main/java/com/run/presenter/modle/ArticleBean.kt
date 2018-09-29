package com.run.presenter.modle

import com.chad.library.adapter.base.entity.MultiItemEntity

class ArticleBean : MultiItemEntity {
    /**
     * details_id : 6
     * category_id : 2
     * title : 去年在山上捡回来的，不知道是小猫还是小狗，不过还是捡回来养了。可爱吧
     * litpic : http://mpic.spriteapp.cn/ugc/2018/03/07/5a9ff53d6c5ae.gif
     * content_type : 1
     * fake_view_max : 0
     * user_name : 搞笑内涵侠
     * user_portrait : http://mpic.spriteapp.cn/profile/large/2017/03/01/58b6ed2126d85_mini.jpg
     * goodpost : 1001
     * badpost : 12
     * publish_begin : 2018-03-19 11:38:29
     * post_type : 0
     */
    var details_id: Int = 0
    var category_id: Int = 0
    var title: String? = null
    var litpic: String? = null
    var content_type: Int = 0
    var fake_view_max: Int = 0
    var user_name: String? = null
    var user_portrait: String? = null
    var goodpost: Int = 0
    var badpost: Int = 0
    var publish_begin: String? = null
    var post_type: Int = 0//1 已点赞，2 一踩
    var collect_type: Int = 0//1没收藏 2,已经收藏
    var content: String? = null
    var user_id: Int = 0//用户的ID;

    var share_count: String? = null

    var litpic_width: Int = 0
    var litpic_height: Int = 0

    var comment_num: Int = 0//评论数量

    private var itemType: Int = 0
    fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    override fun getItemType(): Int {
        return itemType
    }

    companion object {


        //==================================================文章类型分类===============================================================
        val ARTICLE_TEXT = 1
        val ARTICLE_IMAGE = 2
        val ARTICLE_GIFT = 3
        val ARTICLE_IMAGE_PLUS = 4
        val ARTICLE_GIF_PLUS = 5
        val ARTICLE_VEDIO = 6
    }
}
