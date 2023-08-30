package com.example.viewmodellist.ui.screens.songlist

import NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
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
import org.w3c.dom.Comment
import java.text.SimpleDateFormat
import java.util.Date

// "/login/cellphone?phone=18178364626&password=shunjianbaozha1,"

class SongListViewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")
    //TODO 歌单的所有歌曲
    private val _songList =
        mutableStateOf<MutableList<gnonSongList>>(mutableStateListOf())
    val songList: List<gnonSongList> get() = _songList.value //歌单对外的接口

    //TODO 获取系统时间
    val currentTime = mutableStateOf( SimpleDateFormat("HH").format(Date()).toInt())

    //TODO 当前歌单相关的信息
    var id : MutableState<Long> = mutableStateOf(705123491)
    var coverImgUrl = mutableStateOf("")
    var name = mutableStateOf("")
    val userAvatar = mutableStateOf("http://p1.music.126.net/SUeqMM8HOIpHv9Nhl9qt9w==/109951165647004069.jpg")
    val selectedSongIndex = mutableStateOf<Int>(-1)

    //var author = mutableStateOf("")

    //TODO 专辑列表
    private val _songlistData =
        mutableStateOf<MutableList<SongListItem>>(mutableStateListOf())
    val songlistData: List<SongListItem> get() = _songlistData.value

    //TODO 专辑列表（推荐）
    private val _hotPlayList =
        mutableStateOf<MutableList<HotPlayListItem>>(mutableStateListOf())
    val hotPlayList: List<HotPlayListItem> get() = _hotPlayList.value //歌单对外的接口

    //TODO 热门标签
    private val _tagsPlayList = mutableStateOf<MutableList<HotPlayListItem>>(mutableListOf())
    val tagPlayList get() = _tagsPlayList.value

    //TODO 歌单广场顶部导航栏相关
    val selectedTagIndex = mutableStateOf<Int>(0)
    val tagList : List<String> = listOf("推荐","古风","蓝调","欧美","轻音乐","摇滚","民谣","电子","爵士","乡村","说唱","民族","金属")
    val isRec = mutableStateOf(true)
    var nowTag : MutableState<String> = mutableStateOf("推荐")


    //TODO 歌单广场和歌单详情之间的切换状态
    var isShowDetail = mutableStateOf(false)
    var isShowComments = mutableStateOf(false)
    var detailId : MutableState<Long> = mutableStateOf(0)

    //TODO 评论相关

    private val _commentsData = mutableStateOf<MutableList<Comments>>(mutableListOf())
    val commentsData get() = _commentsData.value

    fun fetchSongLists() {
        viewModelScope.launch {
            try {
                val list = repository.getSongList(detailId.value)
                val Mylist = list.map { item ->
                    gnonSongList(
                        id = item.id,
                        url = item.url,
                        name = item.name,
                        artist = item.artist,
                        fee = item.fee
                    )
                }

                _songList.value = Mylist.toMutableList()
                coverImgUrl.value = repository.coverImgUrl
                name.value = repository.name
                Log.d(TAG, "SongList: $songList")
                Log.d(TAG, "coverImgUrl.value:"+ coverImgUrl.value)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchSongListsError: $e")
            }
        }
    }
    fun fetchRecommendSonglistData() {
        viewModelScope.launch {
            try {
                val songlist = repository.getRecommendSonglistData()
                _songlistData.value = songlist.toMutableList()
                Log.d(TAG, "songListRecommendSonglistData: $songlist")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "songListfetchRecommendSonglistDataError: $e")
            }
        }
    }
    fun fetchHotPlaylist() {
        viewModelScope.launch {
            try {
                val songlist = repository.getHotPlaylist()
                _hotPlayList.value = songlist.toMutableList()
                Log.d(TAG, "fetchHotPlaylist: $songlist")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchHotPlaylistError: $e")
            }
        }
    }

    fun fetchTagPlayList(){
        viewModelScope.launch {
            if(nowTag.value != "推荐") {
                try {
                    val tagList = repository.getTagsPlayList(nowTag.value)
                    _tagsPlayList.value = tagList.toMutableList()
                    Log.d(TAG, "tagList:$tagList")
                } catch (e: Exception) {
                    Log.e(TAG, "fetchTagError:$e")
                }
            }
            else
                println("推荐内容")
        }
    }

    fun fetchComments(){
        viewModelScope.launch {
            try {
                val commentsList = repository.getComments(detailId.value)
                _commentsData.value = commentsList.toMutableList()
                Log.d(TAG, "fetchHotPlaylist: $commentsList")
                Log.d(TAG,"大小${commentsList.size}")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchHotPlaylistError: $e")
            }
        }
    }

    fun collectPlayList(){
        viewModelScope.launch {
            try {
                NetworkUtils.https("/playlist/subscribe?t=1&id=$detailId","GET")
                Log.d(TAG,"收藏成功!")
            }catch (e : Exception){
                Log.e(TAG,"收藏失败: $e")
            }
        }
    }
}


class Repository() {
    val gson = Gson()
    var coverImgUrl : String = ""
    var name : String = ""
    suspend fun getSongList(detailId:Long): List<gnonSongList> {
        // TODO 传入歌单ID获取歌单详情
        var result = NetworkUtils.https(url = "/playlist/detail?id=$detailId", method = "GET")

        var response = gson.fromJson(result, JsonObject::class.java)  // 转为Json对象
        val playlistJsonObject = response.getAsJsonObject("playlist")  // 将playList转化为Json对象
        coverImgUrl = playlistJsonObject.get("coverImgUrl").asString
        name = playlistJsonObject.get("name").asString

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

        val res : MutableList<gnonSongList> = mutableListOf()
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

            res.add(element = gnonSongList(
                id = data[i].id,
                url=data[i].url,
                name=name[i].name,
                artist=_author,
                fee = name[i].fee), index = i)

        }
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
    suspend fun getHotPlaylist() :  List<HotPlayListItem>{
        val result = NetworkUtils.https("/top/playlist", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val songlistJsonArray = response.getAsJsonArray("playlists")
        val songlist: List<HotPlayListItem> =
            gson.fromJson(songlistJsonArray, object : TypeToken<List<HotPlayListItem>>() {}.type)
        return songlist
    }
    suspend fun getTagsPlayList(tag : String) : List<HotPlayListItem>{
        val result = NetworkUtils.https("/top/playlist/highquality?cat=$tag","GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val tagListJsonArray = response.getAsJsonArray("playlists")
        val tagList : List<HotPlayListItem> =
            gson.fromJson(tagListJsonArray, object : TypeToken<List<HotPlayListItem>>() {}.type)
        return tagList
    }
    suspend fun getComments(id : Long) : List<Comments>{


        val result = NetworkUtils.https("/comment/playlist?id=$id&limit=100", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val commentsJsonArray = response.getAsJsonArray("comments")
        val comments : List<Comments> = gson.fromJson(commentsJsonArray,object : TypeToken<List<Comments>>() {}.type)
        return comments
    }
}