package me.ghostbear.ui.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

@Composable
fun Chips(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable () -> Unit
) {
    val color = if (selected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .composed {
                modifier
            },
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = color
    ) {
        content()
    }
}