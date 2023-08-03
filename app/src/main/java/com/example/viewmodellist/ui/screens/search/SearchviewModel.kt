package com.example.viewmodellist.ui.screens.search

import NetworkUtils
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
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


class SearchviewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")
    private val _searchHotData =
        mutableStateOf<MutableList<SearchHot>>(mutableStateListOf())
    val searchHotData: List<SearchHot> get() = _searchHotData.value

    private val _searchHotTopData =
        mutableStateOf<MutableList<SearchHotTop>>(mutableStateListOf())
    val searchHotTopData: List<SearchHotTop> get() = _searchHotTopData.value

    private val _searchSuggestData =
        mutableStateOf<MutableList<SearchSuggest>>(mutableStateListOf())
    val searchSuggestData: List<SearchSuggest> get() = _searchSuggestData.value


    private val _resultSongData by lazy { mutableStateListOf<ResultSong>() }
    val resultSongData: List<ResultSong> get() = _resultSongData


    private val _resultSonglistData by lazy { mutableStateListOf<ResultSonglist>() }
    val resultSonglistData: List<ResultSonglist> get() = _resultSonglistData


    fun clearresultSongData() {
        _resultSongData.clear()
    }

    fun clearresultSonglistData() {
        _resultSonglistData.clear()
    }

    fun fetchSearchHotData() {
        viewModelScope.launch {
            try {
                val hots = repository.getSearchHotData()
                val hotsList = hots.map { hot ->
                    SearchHot(
                        first = hot.first
                    )
                }

                _searchHotData.value = hotsList.toMutableList()
                Log.d(TAG, "searchHotData: $searchHotData")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchSearchHotDataError: $e")
            }
        }
    }

    fun fetchSearchHotTopData() {
        viewModelScope.launch {
            try {
                val hots = repository.getSearchHotTopData()
                val hotsList = hots.map { hot ->
                    SearchHotTop(
                        searchWord = hot.searchWord,
                        score = hot.score,
                        content = hot.content,
                        iconUrl = hot.iconUrl,
                    )
                }

                _searchHotTopData.value = hotsList.toMutableList()
                Log.d(TAG, "searchHotTopData: $searchHotTopData")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchSearchHotTopDataError: $e")
            }
        }
    }

    fun fetchSearchSuggestData(keyword: String) {
        viewModelScope.launch {
            try {
                val suggests = repository.getSearchSuggestData(keyword)
                val suggestsList = suggests.map { suggest ->
                    SearchSuggest(
                        keyword = suggest.keyword
                    )
                }

                _searchSuggestData.value = suggestsList.toMutableList()

                Log.d(TAG, "searchSuggestData: $searchSuggestData")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchSearchSuggestDataError: $e")
            }
        }
    }


    fun fetchResultSongData(keyword: String, page: Long) {
        viewModelScope.launch {
            try {
                val songs = repository.getResultSongData(keyword, page)
                val songsList = songs.map { song ->
                    ResultSong(
                        id = song.id,
                        publishTime = song.publishTime,
                        mvid = song.mvid,
                        name = song.name,
                        artist = song.artist,
                        al = song.al
                    )
                }

                _resultSongData.addAll(songsList)

                Log.d(TAG, "resultSongData: $resultSongData")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchResultSongDataError: $e")
            }
        }
    }

    fun fetchResultSonglistData(keyword: String, page: Long) {
        viewModelScope.launch {
            try {
                val lists = repository.getResultSonglistData(keyword, page)
                val songsList = lists.map { song ->
                    ResultSonglist(
                        id = song.id,
                        trackCount = song.trackCount,
                        playCount = song.playCount,
                        coverImgUrl = song.coverImgUrl,
                        name = song.name,
                        creater = song.creater,
                        officialTags = song.officialTags
                    )
                }

                _resultSonglistData.addAll(songsList)

            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchResultSonglistDataError: $e")
            }
        }
    }
}


class Repository {
    private val gson = Gson()


    suspend fun getSearchHotData(): List<SearchHot> {

        val result = NetworkUtils.https("/search/hot", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)

        val hotsobj = response.getAsJsonObject("result")
        val hotsarry = hotsobj.get("hots").asJsonArray


        val hots: List<SearchHot> =
            gson.fromJson(hotsarry, object : TypeToken<List<SearchHot>>() {}.type)


        return hots
    }

    suspend fun getSearchHotTopData(): List<SearchHotTop> {

        val result = NetworkUtils.https("/search/hot/detail", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)

        val data = response.getAsJsonArray("data")


        val hots: List<SearchHotTop> =
            gson.fromJson(data, object : TypeToken<List<SearchHotTop>>() {}.type)


        return hots
    }


    suspend fun getSearchSuggestData(keyword: String): List<SearchSuggest> {

        val result = NetworkUtils.https("/search/suggest?keywords=$keyword&type=mobile", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)

        val obj = response.getAsJsonObject("result")
        val allMatch = obj.get("allMatch").asJsonArray


        val suggests: List<SearchSuggest> =
            gson.fromJson(allMatch, object : TypeToken<List<SearchSuggest>>() {}.type)


        return suggests
    }

    suspend fun getResultSongData(keyword: String, page: Long): List<ResultSong> {


        val result =
            NetworkUtils.https("/search?keywords=$keyword&limit=15&offset=${page * 15}", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)

        val resultobj = response.getAsJsonObject("result")
        val songs = resultobj.get("songs").asJsonArray
        val Rsongs: List<ResultSong> =
            gson.fromJson(songs, object : TypeToken<List<ResultSong>>() {}.type)

        for (i in 0 until songs.size()) {
            val Songobj = songs[i].asJsonObject



            val artistJsonArray = Songobj.getAsJsonArray("artists")
            val artist = artistJsonArray[0].asJsonObject.get("name").asString

            val albumJsonObject = Songobj.getAsJsonObject("album")
            val alias = albumJsonObject.get("name").asString
            val publishTime = albumJsonObject.get("publishTime").asLong

            Rsongs[i].artist = artist
            Rsongs[i].al = alias
            Rsongs[i].publishTime = publishTime

        }
        return Rsongs
    }

    suspend fun getResultSonglistData(keyword: String, page: Long): List<ResultSonglist> {


        val result =
            NetworkUtils.https(
                "/search?keywords=$keyword&limit=15&offset=${page * 15}&type=1000",
                "GET"
            )
        val response = gson.fromJson(result, JsonObject::class.java)

        val resultobj = response.getAsJsonObject("result")
        val playlists = resultobj.get("playlists").asJsonArray
        val Rlists: List<ResultSonglist> =
            gson.fromJson(playlists, object : TypeToken<List<ResultSonglist>>() {}.type)

        for (i in 0 until playlists.size()) {
            val Songlistobj = playlists[i].asJsonObject


            val creatorobj = Songlistobj.getAsJsonObject("creator")
            val creator = creatorobj.get("nickname").asString
            if (Songlistobj.get("officialTags").toString() != "null") {
                Log.d(TAG, "111111${Songlistobj.get("officialTags")}")
                val officialTagsarr = Songlistobj.get("officialTags").asJsonArray
                if (officialTagsarr != null) {
                    val officialTags = mutableStateListOf<String>()
                    for (j in officialTagsarr)
                        officialTags.add(j.asString)
                    Rlists[i].officialTags = officialTags
                }
            }

            Rlists[i].creater = creator


        }
        return Rlists
    }
}