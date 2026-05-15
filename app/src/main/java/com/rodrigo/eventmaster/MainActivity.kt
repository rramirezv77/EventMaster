package com.rodrigo.eventmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rodrigo.eventmaster.screens.AddCategoryScreen
import com.rodrigo.eventmaster.screens.AddEventScreen
import com.rodrigo.eventmaster.screens.EventDetailScreen
import com.rodrigo.eventmaster.screens.HomeScreen
import com.rodrigo.eventmaster.ui.theme.EventMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            EventMasterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = EventMasterRoutes.HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(EventMasterRoutes.HOME) {
                            HomeScreen(navController)
                        }
                        composable(EventMasterRoutes.ADD_CATEGORY) {
                            AddCategoryScreen(navController)
                        }
                        composable(EventMasterRoutes.ADD_EVENT) {
                            AddEventScreen(navController)
                        }
                        composable(
                            route = "${EventMasterRoutes.EVENT_DETAIL}/{eventId}",
                            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                        ) {
                            EventDetailScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

object EventMasterRoutes {
    const val HOME = "home"
    const val ADD_CATEGORY = "addCategory"
    const val ADD_EVENT = "addEvent"
    const val EVENT_DETAIL = "eventDetail"

    fun eventDetail(eventId: Long) = "$EVENT_DETAIL/$eventId"
}
