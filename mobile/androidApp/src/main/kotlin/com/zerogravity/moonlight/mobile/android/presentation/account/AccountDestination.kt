package com.zerogravity.moonlight.mobile.android.presentation.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination

object AccountDestination : NavigationDestination {
    override val route = "account_route"
    override val destination = "account_destination"
}

fun NavGraphBuilder.accountGraph(navigateTo: (String) -> Unit) {
    composable(route = AccountDestination.route) {
        AccountRoute()
    }
}
