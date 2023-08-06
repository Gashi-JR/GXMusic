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
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

import kotlinx.coroutines.launch
import java.io.File
import java.net.InetAddress


class MineviewModel(private val repository: Repository = Repository()) : ViewModel() {


}


class Repository {
    private val gson = Gson()


}