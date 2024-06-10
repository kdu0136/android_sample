package com.example.deeplinkcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.deeplinkcompose.ui.theme.DeepLinkComposeTheme
import android.net.Uri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeepLinkComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = { navController.navigate("detail") }) {
                                Text(text = "To Detail", fontSize = 50.sp)
                            }
                        }
                    }
                    composable(
                        "detail",
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern = "https://compose.link.com/detail/{id}"
                                action = Intent.ACTION_VIEW
                            }
                        ),
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getInt("id")
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "id: $id", fontSize = 50.sp)
                        }
                    }
                }
            }
        }
    }
}
