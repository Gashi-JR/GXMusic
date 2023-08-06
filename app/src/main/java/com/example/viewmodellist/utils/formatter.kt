package com.example.viewmodellist.utils

import okhttp3.Cookie
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object formatter {
    /**
     * 数字转整数 如 100000 转为10万
     * @param {需要转化的数} num
     * @param {需要保留的小数位数} point
     */
    fun tranNumber(num: Long, point: Int): String {
        val numStr = num.toString()

        // 十万以内直接返回
        if (numStr.length < 6) {
            return numStr
        }
        //大于8位数是亿
        else if (numStr.length > 8) {
            val decimal = numStr.substring(
                numStr.length - 8,
                numStr.length - 8 + point
            )
            return parseFloat(parseInt((num / 100000000).toString()).toString() + '.' + decimal).toString() + '亿'
        }
        //大于6位数是十万 (以10W分割 10W以下全部显示)
        else if (numStr.length > 5) {
            val decimal = numStr.substring(
                numStr.length - 4,
                numStr.length - 4 + point
            )

            return parseFloat(parseInt((num / 10000).toString()).toString() + '.' + decimal).toString() + '万'
        } else return ""
    }


    /**
     * 毫秒级的时间戳转换为日期
     * @param {需要转化的数} timestamp
     */
    fun convertTimestampToDateString(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return localDateTime.format(formatter)
    }


    /**
     * 地区代码转换为省级名称
     * @param {地区码} code
     */
    fun getRegionName(code: String): String {
        val regionMap = mapOf(
            "110000" to "北京市",
            "120000" to "天津市",
            "130000" to "河北省",
            "140000" to "山西省",
            "150000" to "内蒙古自治区",
            "210000" to "辽宁省",
            "220000" to "吉林省",
            "230000" to "黑龙江省",
            "310000" to "上海市",
            "320000" to "江苏省",
            "330000" to "浙江省",
            "340000" to "安徽省",
            "350000" to "福建省",
            "360000" to "江西省",
            "370000" to "山东省",
            "410000" to "河南省",
            "420000" to "湖北省",
            "430000" to "湖南省",
            "440000" to "广东省",
            "450000" to "广西壮族自治区",
            "460000" to "海南省",
            "500000" to "重庆市",
            "510000" to "四川省",
            "520000" to "贵州省",
            "530000" to "云南省",
            "540000" to "西藏自治区",
            "610000" to "陕西省",
            "620000" to "甘肃省",
            "630000" to "青海省",
            "640000" to "宁夏回族自治区",
            "650000" to "新疆维吾尔自治区",
            "710000" to "台湾省",
            "810000" to "香港特别行政区",
            "820000" to "澳门特别行政区"
        )

        return regionMap[code] ?: "未知地区"
    }

    /**
     * 省级名称转换为地区代码
     * @param {省级名称} regionName
     */
    fun getCodeByRegionName(regionName: String): String? {
        val regionMap = mapOf(
            "北京市" to "110000",
            "天津市" to "120000",
            "河北省" to "130000",
            "山西省" to "140000",
            "内蒙古自治区" to "150000",
            "辽宁省" to "210000",
            "吉林省" to "220000",
            "黑龙江省" to "230000",
            "上海市" to "310000",
            "江苏省" to "320000",
            "浙江省" to "330000",
            "安徽省" to "340000",
            "福建省" to "350000",
            "江西省" to "360000",
            "山东省" to "370000",
            "河南省" to "410000",
            "湖北省" to "420000",
            "湖南省" to "430000",
            "广东省" to "440000",
            "广西壮族自治区" to "450000",
            "海南省" to "460000",
            "重庆市" to "500000",
            "四川省" to "510000",
            "贵州省" to "520000",
            "云南省" to "530000",
            "西藏自治区" to "540000",
            "陕西省" to "610000",
            "甘肃省" to "620000",
            "青海省" to "630000",
            "宁夏回族自治区" to "640000",
            "新疆维吾尔自治区" to "650000",
            "台湾省" to "710000",
            "香港特别行政区" to "810000",
            "澳门特别行政区" to "820000"
        )

        return regionMap[regionName]
    }


}

