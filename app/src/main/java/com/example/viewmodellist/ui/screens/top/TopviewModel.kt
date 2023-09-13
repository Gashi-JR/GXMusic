package com.example.viewmodellist.ui.screens.top

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


class TopviewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")


    private val _topcardData =
        mutableStateOf<MutableList<TopcardItem>>(mutableStateListOf())
    val topcardData: List<TopcardItem> get() = _topcardData.value

    private val _moretopcardData =
        mutableStateOf<MutableList<TopcardItem>>(mutableStateListOf())
    val moretopcardData: List<TopcardItem> get() = _moretopcardData.value

    private val _topsongData =
        mutableStateOf<MutableList<TopSongItem>>(mutableStateListOf())
    val topsongData: List<TopSongItem> get() = _topsongData.value


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

    fun fetchMoreTopCardData() {
        viewModelScope.launch {
            try {
                val moretopcard = repository.getMoreTopCardData()
                _moretopcardData.value = moretopcard.toMutableList()


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


}


class Repository() {
    val gson = Gson()


    suspend fun getTopCardData(): List<TopcardItem> {

        val result = NetworkUtils.https("/toplist", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val JsonArray = response.getAsJsonArray("list")
        val topcards: List<TopcardItem> =
            gson.fromJson(JsonArray, object : TypeToken<List<TopcardItem>>() {}.type)


        return topcards.subList(5, 14)
    }


    suspend fun getMoreTopCardData(): List<TopcardItem> {

        val result = NetworkUtils.https("/toplist", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val JsonArray = response.getAsJsonArray("list")
        val topcards: List<TopcardItem> =
            gson.fromJson(JsonArray, object : TypeToken<List<TopcardItem>>() {}.type)


        return topcards.subList(14, topcards.size)
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

        return alltopsongs
    }


}