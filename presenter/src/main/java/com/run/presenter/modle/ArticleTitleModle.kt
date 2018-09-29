package com.run.presenter.modle

import com.run.config.modle.BaseModle

class ArticleTitleModle : BaseModle() {

    var data: List<ArticleTitleBean>? = null


    class ArticleTitleBean {

        /**
         * category_id : 0
         * name : 推荐
         */

        var category_id: Int = 0
        var name: String? = null
    }

}
