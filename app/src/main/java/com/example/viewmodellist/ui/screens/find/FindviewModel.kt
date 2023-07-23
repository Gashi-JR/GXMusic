package com.example.viewmodellist.ui.screens.find

import NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellist.utils.Datamodels.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch


class FindviewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建一个 MutableLiveData 对象来存储列表数据。
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


        return topcards.subList(0,5)
    }
}