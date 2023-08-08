package com.example.viewmodellist.ui.screens.mine

import NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellist.utils.Datamodels.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

import kotlinx.coroutines.launch
import java.io.File
import java.net.InetAddress


class MineviewModel(private val repository: Repository = Repository()) : ViewModel() {
    private val _mySonglistData by lazy { mutableStateListOf<MySonglist>() }
    val mySonglistData: List<MySonglist> get() = _mySonglistData


    fun fetchResultSonglistData(id: Long) {
        viewModelScope.launch {
            try {
                val lists = repository.getResultSonglistData(id)
                val songsList = lists.map { song ->
                    MySonglist(
                        id = song.id,
                        trackCount = song.trackCount,
                        playCount = song.playCount,
                        coverImgUrl = song.coverImgUrl,
                        name = song.name,
                        creater = song.creater,
                        tags = song.tags
                    )
                }

                _mySonglistData.addAll(songsList)

            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchResultSonglistDataError: $e")
            }
        }
    }


}


class Repository {
    private val gson = Gson()

    suspend fun getResultSonglistData(id: Long): List<MySonglist> {
        val result =
            NetworkUtils.https(
                "/user/playlist?uid=$id",
                "GET"
            )
        val response = gson.fromJson(result, JsonObject::class.java)

        val playlists = response.getAsJsonArray("playlist")
        val playlist = JsonArray()
        for (i in 1 until playlists.size()) {
            playlist.add(playlists.get(i))
        }

        val Mylists: List<MySonglist> =
            gson.fromJson(playlist, object : TypeToken<List<MySonglist>>() {}.type)


        for (i in 0 until playlist.asJsonArray.size()) {
            val Songlistobj = playlist[i].asJsonObject


            val creatorobj = Songlistobj.getAsJsonObject("creator")
            val creator = creatorobj.get("nickname").asString


            Mylists[i].creater = creator


        }
        return Mylists
    }

}