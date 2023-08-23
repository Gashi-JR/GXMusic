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
    private val _songListData =
        mutableStateOf<MutableList<SongList>>(mutableStateListOf())
    val songListData: List<SongList> get() = _songListData.value //歌单对外的接口

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

        println(tracks)
        var songIdList : String = ""
        for(i in 0 until tracksJsonArray.size()){
            songIdList += tracks[i].id
            if(i != tracksJsonArray.size() -1){
                songIdList += ","
            }
        }
        result = NetworkUtils.https("http://localhost:3001/song/url?id=$songIdList", "GET")
        response = gson.fromJson(result, JsonObject::class.java)
        val songListJsonArray = response.getAsJsonArray("data")
        val data : List<SongList> = gson.fromJson(songListJsonArray, object : TypeToken<List<SongList>>() {}.type)

        var res : List<MySongList> = emptyList<MySongList>()

        for(i in 0 until songListJsonArray.size()){
            res[i].url = data[i].url
            res[i].name = tracks[i].name
        }


        return res
    }
}