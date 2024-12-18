package com.dongun.predictrivebackmigration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dongun.predictrivebackmigration.screens.SheetScreen
import com.dongun.predictrivebackmigration.screens.TextScreen
import com.dongun.predictrivebackmigration.ui.theme.PredictriveBackMigrationTheme
import kotlinx.serialization.Serializable

@Serializable
data object ScreenA

@Serializable
data object ScreenB

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PredictriveBackMigrationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = ScreenA
                    ) {
                        composable<ScreenA> {
                            TextScreen(
                                onButtonClick = {
                                    navController.navigate(ScreenB)
                                }
                            )
                        }
                        composable<ScreenB> {
                            SheetScreen()
                        }
                    }
                }
            }
        }
    }
}