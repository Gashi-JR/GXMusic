package com.example.viewmodellist.ui.screens.find

import NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellist.utils.Datamodels.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch


class FindviewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")
    private val _bannerData =
        mutableStateOf<MutableList<BannerData>>(mutableStateListOf())
    val bannerData: List<BannerData> get() = _bannerData.value

    private val _songlistData =
        mutableStateOf<MutableList<SongListItem>>(mutableStateListOf())
    val songlistData: List<SongListItem> get() = _songlistData.value

    private val _newsongData =
        mutableStateOf<MutableList<NewSongItem>>(mutableStateListOf())
    val newsongData: List<NewSongItem> get() = _newsongData.value

    private val _topcardData =
        mutableStateOf<MutableList<TopcardItem>>(mutableStateListOf())
    val topcardData: List<TopcardItem> get() = _topcardData.value

    private val _topsongData =
        mutableStateOf<MutableList<TopSongItem>>(mutableStateListOf())
    val topsongData: List<TopSongItem> get() = _topsongData.value

    var currentMusic = mutableStateOf(CurrentMusic())


    fun fetchBannerData() {
        viewModelScope.launch {
            try {
                val banners = repository.getBannerData()
                val bannerDataList = banners.map { banner ->
                    BannerData(
                        imageUrl = banner.pic,
                        linkUrl = banner.pic
                    )
                }

                _bannerData.value = bannerDataList.toMutableList()
                Log.d(TAG, "BannerData: $bannerData")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchBannerDataError: $e")
            }
        }
    }

    fun fetchRecommendSonglistData() {
        viewModelScope.launch {
            try {
                val songlist = repository.getRecommendSonglistData()
                _songlistData.value = songlist.toMutableList()
                Log.d(TAG, "RecommendSonglistData: $songlist")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchRecommendSonglistDataError: $e")
            }
        }
    }

    fun fetchNewSongData() {
        viewModelScope.launch {
            try {
                val newsong = repository.getNewSongData()
                _newsongData.value = newsong.toMutableList()

            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchfetchNewSongDataError: $e")
            }
        }
    }

    fun fetchTopCardData() {
        viewModelScope.launch {
            try {
                val topcard = repository.getTopCardData()
                _topcardData.value = topcard.toMutableList()


            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchTopCardDataError: $e")
            }
        }

    }

    fun fetchTopSongData(topcardData: List<TopcardItem>) {
        viewModelScope.launch {
            try {
                topcardData.forEach {
                    val topsong = repository.getTopSongData(it.id)
                    _topsongData.value.addAll(topsong.toMutableList())
                }

            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchTopSongDataError: $e")
            }
        }
    }

    fun fetchCurrentMusicUrl(id: Long) {
        viewModelScope.launch {
            try {
                currentMusic.value.url = repository.getCurrentMusicUrl(id)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchCurrentMusicUrlError: $e")
            }
        }
    }

}


class Repository() {
    val gson = Gson()
    suspend fun getBannerData(): List<Banner> {

        val result = NetworkUtils.https("/banner?type=1", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val bannersJsonArray = response.getAsJsonArray("banners")
        val banners: List<Banner> =
            gson.fromJson(bannersJsonArray, object : TypeToken<List<Banner>>() {}.type)


        return banners
    }

    suspend fun getRecommendSonglistData(): List<SongListItem> {

        val result = NetworkUtils.https("/personalized", "GET")
        Log.d(TAG, "getRecommendSonglistData: $result")
        val response = gson.fromJson(result, JsonObject::class.java)
        val songlistJsonArray = response.getAsJsonArray("result")
        val songlist: List<SongListItem> =
            gson.fromJson(songlistJsonArray, object : TypeToken<List<SongListItem>>() {}.type)


        return songlist
    }

    suspend fun getNewSongData(): List<NewSongItem> {

        val result = NetworkUtils.https("/personalized/newsong?limit=15", "GET")

        val response = gson.fromJson(result, JsonObject::class.java)
        val newsongJsonArray = response.getAsJsonArray("result")

        val newsongs: List<NewSongItem> =
            gson.fromJson(newsongJsonArray, object : TypeToken<List<NewSongItem>>() {}.type)

        for (i in 0 until newsongJsonArray.size()) {
            val newSongJson = newsongJsonArray[i].asJsonObject
            // 获取 song 字段的 JSON 对象alias
            val songJsonObject = newSongJson.getAsJsonObject("song")
            val albumJsonObject = songJsonObject.getAsJsonObject("album")
            val aliasJsonArray = songJsonObject.getAsJsonArray("alias")
            val artistJsonArray = songJsonObject.getAsJsonArray("artists")
            val artist =
                if (aliasJsonArray.size() != 0) aliasJsonArray[0].asString + artistJsonArray[0].asJsonObject.get(
                    "name"
                ).asString
                else
                    artistJsonArray[0].asJsonObject.get("name").asString
            Log.d(TAG, "albumJsonObject: $albumJsonObject")
            // Log.d(TAG, "aliasJsonObject: ${aliasJsonArray[0].asString}")
            Log.d(TAG, "artistJsonArray: ${artistJsonArray[0].asJsonObject.get("name").asString}")


            newsongs[i].artist = artist
        }

        return newsongs
    }


    suspend fun getTopCardData(): List<TopcardItem> {

        val result = NetworkUtils.https("/toplist", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val JsonArray = response.getAsJsonArray("list")
        val topcards: List<TopcardItem> =
            gson.fromJson(JsonArray, object : TypeToken<List<TopcardItem>>() {}.type)


        return topcards.subList(0, 5)
    }

    suspend fun getTopSongData(id: Long): List<TopSongItem> {

        val alltopsongs = mutableListOf<TopSongItem>()
        val result = NetworkUtils.https("/playlist/detail?id=${id}", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)

        val playlist = response.getAsJsonObject("playlist")
        val tracks = playlist.getAsJsonArray("tracks")
        val topsongs: List<TopSongItem> =
            gson.fromJson(tracks, object : TypeToken<List<TopSongItem>>() {}.type)


        for (i in 0 until 3) {

            val SongJson = tracks[i].asJsonObject

            val arJsonArray = SongJson.getAsJsonArray("ar")
            val artist = arJsonArray[0].asJsonObject.get("name").asString
            Log.d(TAG, "artist: $artist")

            val albumJsonObject = SongJson.getAsJsonObject("al")
            val picUrl = albumJsonObject.get("picUrl").asString

            Log.d(TAG, "picUrl: $picUrl")



            topsongs[i].artist = artist
            topsongs[i].picUrl = picUrl

            alltopsongs.addAll(listOf(topsongs[i]))


        }


        //Log.d(TAG, "alltopsongs: $alltopsongs")
        return alltopsongs
    }

    suspend fun getCurrentMusicUrl(id: Long): String {

        val result = NetworkUtils.https("/song/url/v1?id=$id&level=standard", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val JsonArray = response.getAsJsonArray("data")
        val songJsonObject = JsonArray[0].asJsonObject




        return songJsonObject.get("url").asString
    }
}