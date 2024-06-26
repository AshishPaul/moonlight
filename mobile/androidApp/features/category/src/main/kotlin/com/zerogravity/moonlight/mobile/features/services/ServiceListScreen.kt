package com.zerogravity.moonlight.mobile.features.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.zerogravity.moonlight.mobile.common.domain.usecase.HomeFeedUiState
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.serviceListRoute(
    onBackClick: () -> Unit,
) {

    composable(
        route = ServiceListDestination.route,
        arguments = listOf(navArgument(ServiceListDestination.categoryArg) {
            type = NavType.StringType
        }),
        deepLinks = listOf(navDeepLink {
            uriPattern = "moonlight://category/{${ServiceListDestination.categoryArg}}/service"
        })
    ) {
        val viewModel: CategoryViewModel = koinViewModel()
        val uiState by viewModel.homeFeedUiState.collectAsStateWithLifecycle()

        ServiceListScreen(uiState, onBackClick, onRefresh = {
//        viewModel.refresh()
        })
    }


}

@Composable
fun ServiceListScreen(
    uiState: HomeFeedUiState, onBackClick: () -> Unit, onRefresh: () -> Unit
) {

}
