package com.run.presenter.modle


import com.run.config.modle.BaseModle

class WalletModle : BaseModle() {
    var count_income: String? = null//今日收益金币
    var yesterday: String? = null//今日收益金币
    var profit_balance: String? = null//当前余额
    var profit_total: String? = null//累计收益

    var text: String? = null

    var data: List<DataBean>? = null

    class DataBean {

        var money: String? = null
        var time: String? = null


        constructor() {}

        constructor(money: String, time: String) {
            this.money = money
            this.time = time
        }
    }
}
