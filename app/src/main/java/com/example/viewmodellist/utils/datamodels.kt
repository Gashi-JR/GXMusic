package com.example.viewmodellist.utils

import com.google.gson.JsonObject

class Datamodels {

    // TODO: 轮播图相关数据类
    data class BannerData(
        val imageUrl: String,
        val linkUrl: String
    )

    data class Banner(
        val pic: String
    )

    // TODO: 歌单数据类
    data class SongListItem(
        val id: Long,
        val name: String,
        val copywriter: String,
        val playCount: Long,
        val picUrl: String,
    )


    //TODO njl 的类


    //TODO 歌单详情     传入歌单id获取的歌单详情，返回：歌单中音乐的id
    data class Tracks(
        val name: String,
        val id: Long
    )

    //TODO 音乐详情     传入音乐id获取音乐URL
    data class SongList(
        val id: Long,
        val url: String  // 歌曲URL
    )

    data class Names(
        val name: String,
        val fee: Int
    )

    data class Artists(
        val name: String
    )

    data class gnonSongList(
        var id: Long,
        var url: String,
        var name: String,
        var artist: String,
        var fee: Int
    )

    data class HotPlayListItem(
        val id: Long,
        val name: String,
        val playCount: Long,
        val coverImgUrl: String,
    )

    data class Comments(
        val content: String,
        val user: JsonObject,
        val timeStr: String,
        val ipLocation: JsonObject
    )
    //TODO njl 的类


    // TODO: 新歌数据类
    data class NewSongItem(
        val id: Long,
        val picUrl: String,
        val mvid: Long,
        val name: String,
        var artist: String
    )

    // TODO: 排行榜前三数据类
    data class TopcardItem(
        val id: Long,
        val name: String,
        val updateFrequency: String,
        val coverImgUrl: String,
        val description: String
    )

    data class TopSongItem(
        val id: Long,
        var picUrl: String,
        val name: String,
        var artist: String
    )

    // TODO: 当前正在播放的音乐模型
    data class CurrentMusic(
        var id: Long = 0,
        var picUrl: String = "",
        var name: String = "",
        var artist: String = "",
        var url: String = ""
    )

    // TODO: 热搜关键词

    data class SearchHot(
        var first: String = ""
    )

    // TODO: 热搜榜歌曲
    data class SearchHotTop(
        var searchWord: String,
        var score: Long,
        var content: String,
        var iconUrl: String?,
    )

    // TODO:   搜索建议
    data class SearchSuggest(
        var keyword: String = ""
    )

    // TODO: 搜索结果的歌曲
    data class ResultSong(
        val id: Long,
        var publishTime: Long,
        val mvid: Long,
        val name: String,
        var artist: String,
        var al: String,
    )

    // TODO: 搜索结果的歌单
    data class ResultSonglist(
        val id: Long,
        var trackCount: Long,
        var playCount: Long,
        val coverImgUrl: String,
        val name: String,
        var creater: String,
        var officialTags: List<String>?
    )

    // TODO: 二维码登录状态检查结果对象
    data class LoginCheckResult(
        val code: Int,
        val message: String,
        val cookie: String,
    )

    // TODO: 用户信息
    data class UserInfo(
        val uid: Long,
        val nickname: String = "",
        val level: Int,
        val followeds: Int,
        val follows: Int,
        val avatarUrl: String,
        val birthday: Long,
        val createTime: Long,
        val province: Long,
        val signature: String,
        val gender: Int
    )


    // TODO: 我收藏的歌单
    data class MySonglist(
        val id: Long,
        var trackCount: Long,
        var playCount: Long,
        val coverImgUrl: String,
        val name: String,
        var creater: String,
        var tags: List<String>?
    )
}