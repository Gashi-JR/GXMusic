package com.example.viewmodellist.ui.screens.login

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


class LoginviewModel(private val repository: Repository = Repository()) : ViewModel() {

    var qrimg: MutableState<String> = mutableStateOf("")
    var key: MutableState<String> = mutableStateOf("")
    var result: MutableState<LoginChechResult> = mutableStateOf(LoginChechResult(0, "", ""))

    var User: MutableState<UserInfo> = mutableStateOf(UserInfo(0, "", "", 0, 0, 0, "", 0))


    fun fetchLoginQRcode() {
        viewModelScope.launch {
            try {
                key.value = repository.getLoginkey()
                qrimg.value = repository.getLoginQRimg(key.value)

            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchLoginQRcodeError: $e")
            }
        }
    }

    fun fetchLoginStatus() {
        viewModelScope.launch {
            try {
                result.value = repository.getLoginQRStatus(key.value)
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchLoginStatusError: $e")
            }
        }
    }

    fun fetchUserUserInfo() {
        viewModelScope.launch {
            try {
                User.value = repository.getLoginUserInfo()
                Log.d(TAG, "fetchUserUserInfo: ${User.value}")
            } catch (e: Exception) {
                // 处理异常情况
                Log.e(TAG, "fetchUserUserInfoError: $e")
            }
        }
    }
}


class Repository {
    private val gson = Gson()


    suspend fun getLoginkey(): String {

        val results =
            NetworkUtils.https("/login/qr/key?timestamp=${System.currentTimeMillis()}", "GET")
        val response = gson.fromJson(results, JsonObject::class.java)

        val data = response.getAsJsonObject("data")
        val unikey = data.get("unikey").asString


        return unikey

    }

    suspend fun getLoginQRimg(key: String): String {

        val results =
            NetworkUtils.https(
                "/login/qr/create?key=${key}&qrimg=1&timestamp=${System.currentTimeMillis()}",
                "GET"
            )
        val response = gson.fromJson(results, JsonObject::class.java)


        val data = response.getAsJsonObject("data")
        val qrimg = data.get("qrimg").asString


        return qrimg

    }

    suspend fun getLoginQRStatus(key: String): LoginChechResult {

        val results =
            NetworkUtils.https(
                "/login/qr/check?key=${key}&timestamp=${System.currentTimeMillis()}",
                "GET"
            )
//        val results =
//            NetworkUtils.https(
//                "/login/qr/check?key=80b76e68-69d6-4cfa-b850-132ca3c77e2d&timestamp=1691131843929",
//                "GET"
//            )
        val response = gson.fromJson(results, JsonObject::class.java)


        val code = response.get("code").asInt
        val message = response.get("message").asString
        val cookie = response.get("cookie").asString


        return LoginChechResult(code, message, cookie)

    }


    suspend fun getLoginUserInfo(): UserInfo {

        val results =
            NetworkUtils.https(
                "/login/status",
                "GET"
            )
        val response = gson.fromJson(results, JsonObject::class.java)


        val data = response.getAsJsonObject("data")

        val account = data.get("account").asJsonObject
        val profile = data.get("profile").asJsonObject

        val uid = account.get("id").asLong
        val nickname = profile.get("nickname").asString
        val avatarUrl = profile.get("avatarUrl").asString
        val birthday = profile.get("birthday").asLong
        val createTime = profile.get("createTime").asLong
        val province = profile.get("province").asLong
        val signature = profile.get("signature").asString
        val gender = profile.get("gender").asInt  //0未知1男2女

        return UserInfo(uid, nickname, avatarUrl, birthday, createTime, province, signature, gender)

    }


}