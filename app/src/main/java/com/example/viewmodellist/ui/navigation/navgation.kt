package com.example.viewmodellist.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.screens.find.Find
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.login.Login
import com.example.viewmodellist.ui.screens.mine.Mine
import com.example.viewmodellist.ui.screens.songlist.SongList
import com.example.viewmodellist.ui.screens.top.Top

object NavGraph {
    // TODO:把底部导航栏的依赖变量抽出

    // 初始化选中的选项卡索引
 var selectedTabIndex by mutableStateOf(0)

    init {
        // 在 init 块中设置初始值
        selectedTabIndex = 0
    }




    fun toSingleInstance(controller: NavHostController, route: String) {
        val findRoute = controller.findDestination(route)
        if (findRoute == null) {
            controller.navigate(route)
        } else {
            controller.popBackStack()
            controller.navigate(route)
        }
    }

    @Composable
    fun create(navHostController: NavHostController) {


        NavHost(navController = navHostController, startDestination = "find") {
            composable("login") { Login() }
            composable("find") { Find(findviewModel = FindviewModel()) }
            composable("songlist") { SongList() }
            composable("top") { Top() }
            composable("mine") { Mine() }

        }

    }

}