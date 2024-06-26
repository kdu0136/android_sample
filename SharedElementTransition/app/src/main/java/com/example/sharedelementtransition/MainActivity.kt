package com.example.sharedelementtransition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sharedelementtransition.ui.theme.SharedElementTransitionTheme

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedElementTransitionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SharedTransitionLayout {
                            val navController = rememberNavController()
                            NavHost(
                                navController = navController,
                                startDestination = "list"
                            ) {
                                composable("list") {
                                    ListScreen(
                                        onItemClick = { resId, text ->
                                            navController.navigate("detail/$resId/$text")
                                        },
                                        animatedVisibilityScope = this
                                    )
                                }
                                composable(
                                    route = "detail/{resId}/{text}",
                                    arguments = listOf(
                                        navArgument("resId") {
                                            type = NavType.IntType
                                        },
                                        navArgument("text") {
                                            type = NavType.StringType
                                        },
                                    )
                                ) {
                                    val resId = it.arguments?.getInt("resId") ?: 0
                                    val text = it.arguments?.getString("text") ?: ""
                                    DetailScreen(
                                        resId = resId,
                                        text = text,
                                        animatedVisibilityScope = this
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
