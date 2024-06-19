package com.example.material3

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CategoriesLazyColumnScreen() {
    Scaffold { values ->
        val namesList = names.map {
            Category(
                name = it.key.toString(),
                items = it.value
            )
        }
        val alphaList = names.keys.sorted()
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        Box(
            modifier = Modifier.padding(values),
        ) {
            CategorizedLazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                categories = namesList,
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
            ) {
                alphaList.forEachIndexed { index, letter ->
                    Text(
                        text = letter.toString(),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                scope.launch {
                                    var scrollIndex = index
                                    for (category in namesList) {
                                        if (category.name == letter.toString()) break
                                        scrollIndex += category.items.size
                                    }
                                    listState.scrollToItem(scrollIndex)
                                }
                            }
                    )
                }
            }
        }
    }
}

data class Category(
    val name: String,
    val items: List<String>,
)

@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategorizedLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState,
    categories: List<Category>,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
    ) {
        categories.forEach { category ->
            stickyHeader {
                CategoryHeader(text = category.name)
            }
            items(category.items) { text ->
                CategoryItem(text = text)
            }
        }
    }
}