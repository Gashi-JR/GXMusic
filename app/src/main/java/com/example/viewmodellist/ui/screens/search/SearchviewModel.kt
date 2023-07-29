package com.example.viewmodellist.ui.screens.search

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


class SearchviewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建 MutableList 对象来存储列表数据。
    @SuppressLint("MutableCollectionMutableState")
    private val _searchHotData =
        mutableStateOf<MutableList<SearchHot>>(mutableStateListOf())
    val searchHotData: List<SearchHot> get() = _searchHotData.value

    private val _searchHotTopData =
        mutableStateOf<MutableList<SearchHotTop>>(mutableStateListOf())
    val searchHotTopData: List<SearchHotTop> get() = _searchHotTopData.value


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
}


class Repository() {
    val gson = Gson()


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
}