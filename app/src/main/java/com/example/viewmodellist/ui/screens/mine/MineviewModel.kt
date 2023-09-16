package com.example.viewmodellist.ui.screens.mine

import com.example.viewmodellist.utils.NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellist.utils.Datamodels.MySonglist
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch


@SuppressLint("MutableCollectionMutableState")
class MineviewModel(private val repository: Repository = Repository()) : ViewModel() {
    private var _mySonglistData by mutableStateOf<MutableList<MySonglist>>(mutableStateListOf())
    val mySonglistData: List<MySonglist> get() = _mySonglistData

    var duplicated by mutableStateOf(false)


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

                _mySonglistData = songsList.toMutableList()

            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchResultSonglistDataError: $e")
            }
        }
    }

    fun checkNickname(name: String) {
        viewModelScope.launch {
            try {
                duplicated = repository.getcheckNicknameData(name)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "checkNicknameDataError: $e")
            }
        }

    }

    fun modifyUserInfo(
        birthday: Long,
        province: Long,
        signature: String,
        gender: Int
    ) {
        viewModelScope.launch {
            try {
                repository.modifyUserInfoData(birthday, province, signature, gender)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "modifyUserInfoDataError: $e")
            }
        }

    }

    fun modifyUsername(
        name: String,
    ) {
        viewModelScope.launch {
            try {
                repository.modifyUsernameData(name)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "modifyUsernameDataError: $e")
            }
        }

    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout()
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "logoutError: $e")
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

    suspend fun getcheckNicknameData(name: String): Boolean {
        val result =
            NetworkUtils.https(
                "/nickname/check?nickname=$name",
                "GET"
            )
        val response = gson.fromJson(result, JsonObject::class.java)


        return response.get("duplicated").asBoolean
    }

    suspend fun modifyUserInfoData(
        birthday: Long,
        province: Long,
        signature: String,
        gender: Int
    ): String {
        val result =
            NetworkUtils.https(
                "/user/update?gender=$gender&signature=$signature&city=${
                    if (province.toInt() == 110000 || province.toInt() == 120000 || province.toInt() == 310000 || province.toInt() == 500000)
                        province + 101
                    else
                        province + 100
                }&birthday=$birthday&province=$province",
                "GET"
            )

        val response = gson.fromJson(result, JsonObject::class.java)


        return response.get("code").asString
    }

    suspend fun modifyUsernameData(
        name: String,
    ): String {
        val result =
            NetworkUtils.https(
                "/user/update?nickname=$name",
                "GET"
            )

        val response = gson.fromJson(result, JsonObject::class.java)


        return response.get("code").asString
    }

    suspend fun logout(): String {
        val result =
            NetworkUtils.https(
                "/logout",
                "GET"
            )

        val response = gson.fromJson(result, JsonObject::class.java)


        return response.get("code").asString
    }
}