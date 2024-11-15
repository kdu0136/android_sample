package com.dongun.composeswipetoreveal

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: ImageVector,
    tint: Color = Color.White,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.background(backgroundColor),
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
        )
    }
}
