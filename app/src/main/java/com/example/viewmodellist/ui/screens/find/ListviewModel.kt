package com.example.viewmodellist.ui.screens.find

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class DataItem(var text: String) {


}


class ListviewModel : ViewModel() {
    private val _listData = mutableStateListOf<DataItem>()
    val listData: SnapshotStateList<DataItem> get() = _listData

    fun add(item: DataItem) {
        listData.add(item)
    }

    fun remove(item: DataItem) {
        listData.remove(item)
    }


}