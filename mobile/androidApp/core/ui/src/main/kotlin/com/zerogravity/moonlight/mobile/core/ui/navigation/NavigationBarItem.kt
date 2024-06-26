package com.zerogravity.moonlight.mobile.core.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination

data class NavigationBarItem(
    val navigationDestination: TopLevelDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

