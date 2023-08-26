package com.example.viewmodellist.ui.screens.songlist

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


class SongListViewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")
    private val _songList =
        mutableStateOf<MutableList<MySongList>>(mutableStateListOf())
    val songList: List<MySongList> get() = _songList.value //歌单对外的接口
    var index = 1;
    fun fetchSongLists() {
        viewModelScope.launch {
            try {
                val list = repository.getSongList()
                val Mylist = list.map { item ->
                    MySongList(
                        url = item.url,
                        name = item.name,
                        author = item.author
                    )
                }

                _songList.value = Mylist.toMutableList()
                Log.d(TAG, "SongList: $songList")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchSongListsError: $e")
            }
        }
    }

}


class Repository() {
    val gson = Gson()

    suspend fun getSongList(): List<MySongList> {


        var result = NetworkUtils.https(url = "/playlist/detail?id=2195384925", method = "GET")
        var response = gson.fromJson(result, JsonObject::class.java)
        val playlistJsonObject = response.getAsJsonObject("playlist")
        val tracksJsonArray = playlistJsonObject.getAsJsonArray("tracks")
        val tracks : List<Tracks> =
            gson.fromJson(tracksJsonArray, object : TypeToken<List<Tracks>>() {}.type)

        println("tracks:$tracks")
        var songIdList : String = ""
        for(i in 0 until tracksJsonArray.size()){
            songIdList += tracks[i].id
            if(i != tracksJsonArray.size() -1){
                songIdList += ","
            }
        }
        result = NetworkUtils.https("/song/url?id=$songIdList", "GET")
        response = gson.fromJson(result, JsonObject::class.java)
        val songListJsonArray = response.getAsJsonArray("data")
        val data : List<SongList> = gson.fromJson(songListJsonArray, object : TypeToken<List<SongList>>() {}.type)

        val res : MutableList<MySongList> = mutableListOf()

        for(i in 0 until songListJsonArray.size()){

            res.add(element = MySongList(
                url=data[i].url,
                name=tracks[i].name,
                author="123"), index = i)

        }
        try {
            Log.d(TAG, "res:$res")
        }catch (e : Exception){
            Log.e(TAG, "getSongList() error: $e")
        }
        return res
    }
}