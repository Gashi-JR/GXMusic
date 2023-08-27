package com.example.viewmodellist.ui.screens.songlist

import NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellist.utils.Datamodels.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
// "/login/cellphone?phone=18178364626&password=shunjianbaozha1,"

class SongListViewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")
    private val _songList =
        mutableStateOf<MutableList<MySongList>>(mutableStateListOf())
    val songList: List<MySongList> get() = _songList.value //歌单对外的接口
    var coverImgUrl = mutableStateOf("")
    fun fetchSongLists() {
        viewModelScope.launch {
            try {
                val list = repository.getSongList()
                val Mylist = list.map { item ->
                    MySongList(
                        id = item.id,
                        url = item.url,
                        name = item.name,
                        artist = item.artist
                    )
                }

                _songList.value = Mylist.toMutableList()
                coverImgUrl.value = repository.coverImgUrl

                Log.d(TAG, "SongList: $songList")
                Log.d(TAG, "coverImgUrl.value:"+ coverImgUrl.value)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchSongListsError: $e")
            }
        }
    }

}


class Repository() {
    val gson = Gson()
    var coverImgUrl : String = ""
    suspend fun getSongList(): List<MySongList> {
        // TODO 传入歌单ID获取歌单详情
        var result = NetworkUtils.https(url = "/playlist/detail?id=2195384925", method = "GET")

        var response = gson.fromJson(result, JsonObject::class.java)  // 转为Json对象
        val playlistJsonObject = response.getAsJsonObject("playlist")  // 将playList转化为Json对象
        coverImgUrl = playlistJsonObject.get("coverImgUrl").asString


        val tracksJsonArray = playlistJsonObject.getAsJsonArray("tracks") // playList中的tracks转为Json数组

        val tracks : List<Tracks> =
            gson.fromJson(tracksJsonArray, object : TypeToken<List<Tracks>>() {}.type)  // Json数组转为自定义列表
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
        songIdList = ""
        for(i in 0 until songListJsonArray.size()){
            songIdList += data[i].id
            if(i != tracksJsonArray.size() -1){
                songIdList += ","
            }
        }
        result = NetworkUtils.https("/song/detail?ids=$songIdList", "GET")
        response = gson.fromJson(result, JsonObject::class.java)
        //val songsJsonArray = response.getAsJsonArray("songs")
        val nameJsonArray = response.getAsJsonArray("songs")
        val name : List<Names> = gson.fromJson(nameJsonArray, object : TypeToken<List<Names>>() {}.type)

        for(i in 0 until songListJsonArray.size()){

            var _author : String = "";

            val artistsJsonArray =  tracksJsonArray[i].asJsonObject.getAsJsonArray("ar")
            val artists : List<Artists> = gson.fromJson(artistsJsonArray, object : TypeToken<List<Artists>>() {}.type)
            for(j in 0 until artistsJsonArray.size()){
                if(j != artistsJsonArray.size() -1){
                    _author += artists[j].name
                    _author += " / "
                }
                else{
                    _author += artists[j].name
                }
            }

            res.add(element = MySongList(
                id = data[i].id,
                url=data[i].url,
                name=name[i].name,
                artist=_author), index = i)

        }
/*        try {
            Log.d(TAG, "res:$res")
        }catch (e : Exception){
            Log.e(TAG, "getSongList() error: $e")
        }*/
        return res
    }
    suspend fun getRecommendSonglistData(): List<SongListItem> {

        val result = NetworkUtils.https("/personalized?limit=6", "GET")
        Log.d(TAG, "getRecommendSonglistData: $result")
        val response = gson.fromJson(result, JsonObject::class.java)
        val songlistJsonArray = response.getAsJsonArray("result")
        val songlist: List<SongListItem> =
            gson.fromJson(songlistJsonArray, object : TypeToken<List<SongListItem>>() {}.type)


        return songlist
    }
}