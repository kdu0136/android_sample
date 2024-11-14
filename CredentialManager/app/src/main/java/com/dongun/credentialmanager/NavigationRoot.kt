package com.dongun.credentialmanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dongun.credentialmanager.login.LoginScreen
import com.dongun.credentialmanager.login.LoginViewModel
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data class LoggedInRoute(val username: String)

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRoute,
    ) {
        composable<LoginRoute> {
            val viewModel = viewModel<LoginViewModel>()
            LoginScreen(
                state = viewModel.state,
                onAction = viewModel::onAction,
                onLoggedIn = { username ->
                    navController.navigate(LoggedInRoute(username))
                }
            )
        }

        composable<LoggedInRoute> {
            val username = it.toRoute<LoggedInRoute>().username
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "logged in as $username"
                )
            }
        }
    }
}

