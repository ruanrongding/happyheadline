package com.run.presenter.modle


import com.run.config.modle.BaseModle

/**
 * Created by xiaor on 2018/4/2.
 */

class InviteModle : BaseModle() {

    /**
     * status : 200
     * code : 10002
     * msg : 亲~~！收徒系统维护中，请稍后再来！
     * explain :
     *
     *1、收徒奖励：有效收徒奖励5元，发放规则：徒弟转发阅读收益满1元奖励1元，徒弟首次提现再奖励2元，徒弟二次提现又奖励2元！
     *
     * 2、好友分成：好友分享文章获得的奖励你还可以获得20%的分成，永久有效！好友的好友你还能得10%分成！一共两级共30%分成！
     * text : 12月20【特赚】上线即活动、不定期阅读收益翻倍，收徒提成翻倍，实力平台值得信赖！  ①单价0.12元。注册送1元！   ②支持微信，群，圈，友！   ③2级徒弟30%提成！收徒5元: ④首次5元现最快秒到账！   注册地址：http://st.suiming.net.cn/accept/to/AGdQZwYwVDAHbQUpUGdWLQVr.do切记哦～将入口网址复制粘贴到QQ浏览器或UC浏览器操作，否则注册不成功
     * count_invite : 0
     * list : 0
     * count_apprentice : null
     * count_all : null
     * url : http://V.suiming.net.cn/accept/AGdQZwYwVDAHbQUpUGdWLQVr.do
     * friend_url : http://s.suiming.net.cn/accept/AGdQZwYwVDAHbQUpUGdWLQVr.do
     * share : {"title":"我为立赚带盐，邀请您也一起来~","share_picture":"http://liz.szhuanww.com/static/img/icon.png","content_describe":"大家一起来，赚$_$得很简单，手指一点，就可以轻松得$_$收溢！！"}
     */

    private var explain: String? = null
    private var text: String? = null
    private var count_invite: Int = 0
    private var list: Int = 0
    private var count_apprentice: String? = null
    private var count_all: String? = null
    private var url: String? = null
    private var friend_url: String? = null
    private var share: ShareBean? = null
    private var activity_type: Int = 0
    private var invite_top_img: String? = null

    fun getActivity_type(): Int {
        return activity_type
    }

    fun setActivity_type(activity_type: Int) {
        this.activity_type = activity_type
    }

    fun getInvite_top_img(): String? {
        return invite_top_img
    }

    fun setInvite_top_img(invite_top_img: String) {
        this.invite_top_img = invite_top_img
    }

    fun getExplain(): String? {
        return explain
    }

    fun setExplain(explain: String) {
        this.explain = explain
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    fun getCount_invite(): Int {
        return count_invite
    }

    fun setCount_invite(count_invite: Int) {
        this.count_invite = count_invite
    }

    fun getList(): Int {
        return list
    }

    fun setList(list: Int) {
        this.list = list
    }

    fun getCount_apprentice(): String? {
        return count_apprentice
    }

    fun setCount_apprentice(count_apprentice: String) {
        this.count_apprentice = count_apprentice
    }

    fun getCount_all(): String? {
        return count_all
    }

    fun setCount_all(count_all: String) {
        this.count_all = count_all
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getFriend_url(): String? {
        return friend_url
    }

    fun setFriend_url(friend_url: String) {
        this.friend_url = friend_url
    }

    fun getShare(): ShareBean? {
        return share
    }

    fun setShare(share: ShareBean) {
        this.share = share
    }

    class ShareBean {
        /**
         * title : 我为立赚带盐，邀请您也一起来~
         * share_picture : http://liz.szhuanww.com/static/img/icon.png
         * content_describe : 大家一起来，赚$_$得很简单，手指一点，就可以轻松得$_$收溢！！
         */

        var title: String? = null
        var share_picture: String? = null
        var content_describe: String? = null
    }
}
