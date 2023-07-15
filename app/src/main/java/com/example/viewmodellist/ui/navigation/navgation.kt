package com.example.viewmodellist.ui.navigation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viewmodellist.MainActivity
import com.example.viewmodellist.ui.screens.find.Find
import com.example.viewmodellist.ui.screens.find.ListviewModel
import com.example.viewmodellist.ui.screens.login.Login
import com.example.viewmodellist.ui.screens.mine.Mine
import com.example.viewmodellist.ui.screens.songlist.SongList
import com.example.viewmodellist.ui.screens.top.Top

object NavGraph {
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
            composable("find") { Find(listviewModel = ListviewModel()) }
            composable("songlist") { SongList() }
            composable("top") { Top() }
            composable("mine") { Mine() }

        }

    }

}