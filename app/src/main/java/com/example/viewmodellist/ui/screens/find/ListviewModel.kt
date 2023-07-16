package com.example.viewmodellist.ui.screens.find

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel


/**
 * 轮播图数据
 */
data class BannerData(
    val imageUrl: String,
    val linkUrl: String
)

class DataviewModel : ViewModel() {
    private val _BannerdataData = mutableStateListOf<BannerData>()
    val bannerdata: SnapshotStateList<BannerData> get() = _BannerdataData


}