package com.example.listpanescaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.listpanescaffold.ui.theme.ListPaneScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListPaneScaffoldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var selectedItemIndex by remember {
                        mutableIntStateOf(0)
                    }
                    val windowWidthClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
                    NavigationSuiteScaffold(
                        modifier = Modifier.padding(innerPadding),
                        navigationSuiteItems = {
                            Screen.entries.forEachIndexed { index, screen ->
                                item(
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = index
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = {
                                        Text(text = screen.title)
                                    }
                                )
                            }
                        },
                        layoutType = if(windowWidthClass == WindowWidthSizeClass.EXPANDED) {
                            NavigationSuiteType.NavigationDrawer
                        } else {
                            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                                currentWindowAdaptiveInfo()
                            )
                        }
                    ) {
                        if (selectedItemIndex == 0) {
                            ListDetailLayout(modifier = Modifier.fillMaxSize())
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = Screen.entries[selectedItemIndex].title)
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Screen(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    SEARCH("Search", Icons.Default.Search),
    SETTINGS("Settings", Icons.Default.Settings),
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailLayout(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(100) {
                    Text(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    content = "Item $it"
                                )
                            },
                        text = "Item $it"
                    )
                }
            }
        },
        detailPane = {
            val content = navigator.currentDestination?.content?.toString() ?: "Select an item"
            AnimatedPane {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = content)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AssistChip(
                            onClick = {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Extra,
                                    content = "Option 1"
                                )
                            },
                            label = {
                                Text(text = "Option 1")
                            }
                        )
                        AssistChip(
                            onClick = {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Extra,
                                    content = "Option 2"
                                )
                            },
                            label = {
                                Text(text = "Option 2")
                            }
                        )
                    }
                }
            }
        },
        extraPane = {
            val content = navigator.currentDestination?.content?.toString() ?: "Select an item"
            AnimatedPane {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = content)
                }
            }
        }
    )
}
