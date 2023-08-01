package com.example.viewmodellist.utils

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
}

