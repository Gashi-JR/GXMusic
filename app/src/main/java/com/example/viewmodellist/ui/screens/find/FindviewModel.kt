package com.example.viewmodellist.ui.screens.find

import NetworkUtils
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellist.ui.components.find.SongItem
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch


/**
 * 轮播图数据
 */
data class BannerData(
    val imageUrl: String,
    val linkUrl: String
)

data class Banner(
    val pic: String
)

class FindviewModel(private val repository: Repository = Repository()) : ViewModel() {
    //创建一个 MutableLiveData 对象来存储列表数据。
//    private val _BannerData = MutableLiveData<List<BannerData>>()
//    val bannerData: List<BannerData>? get() = _BannerData.value
    private val _bannerData = mutableStateOf<MutableList<BannerData>>(mutableStateListOf())
    val bannerData: List<BannerData> get() = _bannerData.value

    fun createSongItems(): List<SongItem> {
        val songItems = mutableListOf<SongItem>()
        repeat(18) {
            songItems.add(
                SongItem(
                    imageUrl = "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
                    title = "阿发发发疯阿发复旦复华",
                    playCount = 6346363636
                )
            )
        }
        return songItems
    }

    val songItems = createSongItems()


    fun fetchBannerData() {
        viewModelScope.launch {
            try {
                val banners = repository.getBannerData()
                val bannerDataList = banners.map { banner ->
                    BannerData(
                        imageUrl = banner.pic,
                        linkUrl = banner.pic
                    )
                }

                _bannerData.value = bannerDataList.toMutableList()
                Log.d(TAG, "BannerData: $bannerData")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchBannerDataError: $e")
            }
        }
    }
}

class Repository() {
    val gson = Gson()
    suspend fun getBannerData(): List<Banner> {

        val result = NetworkUtils.https("/banner?type=1", "GET")
        val response = gson.fromJson(result, JsonObject::class.java)
        val bannersJsonArray = response.getAsJsonArray("banners")
        val banners: List<Banner> =
            gson.fromJson(bannersJsonArray, object : TypeToken<List<Banner>>() {}.type)


        return banners
    }
}