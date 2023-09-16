package com.example.viewmodellist.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.Find
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.mine.Mine
import com.example.viewmodellist.ui.screens.search.SearchviewModel
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
    fun Create(navHostController: NavHostController) {


        NavHost(navController = navHostController, startDestination = "find") {
           // composable("login") { Login(loginviewModel = ) }
            composable("find") {
                Find(
                    FindviewModel(), MediaPlayerViewModel(),
                    SearchviewModel()
                )
            }
            composable("songlist") { SongList() }
            composable("top") { Top() }
            composable("mine") { Mine() }

        }

    }

}