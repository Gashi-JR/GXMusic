package com.example.viewmodellist.ui.screens.top

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

    @SuppressLint("MutableCollectionMutableState")
    private val _moretopcardData =
        mutableStateOf<MutableList<TopcardItem>>(mutableStateListOf())
    val moretopcardData: List<TopcardItem> get() = _moretopcardData.value


    var nowindex by mutableStateOf(-1)
    var shouldUpdateIndex = mutableStateOf(false)

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


}


class Repository {
    private val gson = Gson()


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

        val top1 = topcards.subList(14, topcards.size - 7)
        val top2 = topcards[topcards.size - 1]
        return top1 + top2
    }


}