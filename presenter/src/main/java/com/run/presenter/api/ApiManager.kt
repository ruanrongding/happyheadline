package com.run.presenter.api


import com.run.common.BaseApplication
import com.run.common.utils.UEncrypt
import com.run.common.utils.URetrofit
import com.run.config.AppConstants
import com.run.config.modle.BaseModle
import com.run.presenter.LoginHelper
import com.run.presenter.modle.*
import io.reactivex.Observable
import org.json.JSONException
import org.json.JSONObject

object ApiManager {

    private var apiService: ApiService? = null
    private val instance: ApiService
        get() {
            if (apiService == null) {
                synchronized(ApiManager::class.java) {
                    if (apiService == null) {
                        apiService = URetrofit.getInstance(AppConstants.BASE_URL, BaseApplication.context!!)!!.create(ApiService::class.java)
                    }
                }
            }
            return this!!.apiService!!
        }

    /**
     * 记录激活数据
     */
    fun first(imei: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("imei", imei)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return ApiManager.instance.first(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 获取最热文章列表
     */
    fun articleessence(page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.articleessence(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 获取最热文章列表
     */
    fun articlelist(catid: Int, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("catid", catid)
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)

        return instance.articlelist(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 发现最新
     */
    fun discover_newest(page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.discover_newest(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 发现热门
     */
    fun discover_hot(page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.discover_hot(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 今日推荐
     */
    fun recommend_day(page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.recommend_day(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 一周和一月最火
     */
    fun seniority_details(type: String, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("type", type)
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.seniority_details(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /*
     *个人中心-帖子
     */
    fun my_article(user_id: Int, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("user_id", user_id)
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.my_article(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 分享帖子
     */
    fun ym_share(user_id: Int, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("user_id", user_id)
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.ym_share(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 所属圈子文章
     */
    fun circle_article(circle_id: Int, circle_type: String, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("circle_id", circle_id)
        jsonObject.put("circle_type", circle_type)
        jsonObject.put("page", page)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)

        return instance.circle_article(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 获取栏目分类
     */
    fun article(): Observable<ArticleTitleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.article(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 获取文章详情页面
     */
    fun articledetail(articleid: Int): Observable<ArticleDetailModle> {
        val jsonObject = JSONObject()
        jsonObject.put("articleid", articleid)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.articledetail(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 圈子列表
     */
    fun circle_list(): Observable<CircleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.circle_list(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 关注的圈子列表
     */
    fun circle_attention_list(): Observable<CircleModle> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.circle_attention_list(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    //======================================首页用户数据=============================================================================================

    /**
     * 请求首页数据
     */
    fun index(): Observable<UserModle> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.index(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 用户信息详情
     */
    fun user_info(): Observable<UserInfoModile> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.user_info(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


}
