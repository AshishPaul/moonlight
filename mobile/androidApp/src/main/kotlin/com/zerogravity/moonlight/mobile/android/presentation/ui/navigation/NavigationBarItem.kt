package com.zerogravity.moonlight.mobile.android.presentation.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItem(
    val navigationDestination: TopLevelDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

