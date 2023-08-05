package com.example.viewmodellist.utils

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
        val playcount: Long,
        val playCount: Long,
        val picUrl: String,
    )

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
    data class LoginChechResult(
        val code: Int,
        val message: String,
        val cookie: String,
    )

    // TODO: 用户信息
    data class UserInfo(
        val uid: Long,
        val nickname: String,
        val avatarUrl: String,
        val birthday: Long,
        val createTime: Long,
        val province: Long,
        val signature: String,
        val gender: Int
    )
}