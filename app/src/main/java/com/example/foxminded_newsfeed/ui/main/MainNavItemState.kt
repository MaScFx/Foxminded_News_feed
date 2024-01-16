@file:JvmName("NavItemStateKt")

package com.example.foxminded_newsfeed.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.foxminded_newsfeed.R

@Composable
fun getNavItemState(): List<NavItemState> {
    return listOf<NavItemState>(
        NavItemState(
            contentDescription = stringResource(R.string.all_news),
            icon = ImageVector.vectorResource(R.drawable.eye),
        ), NavItemState(
            contentDescription = stringResource(R.string.select_news_provider),
            icon = ImageVector.vectorResource(R.drawable.layers),
        ), NavItemState(
            contentDescription = stringResource(R.string.selected_articles),
            icon = ImageVector.vectorResource(R.drawable.bookmark_selected),
        )
    )
}