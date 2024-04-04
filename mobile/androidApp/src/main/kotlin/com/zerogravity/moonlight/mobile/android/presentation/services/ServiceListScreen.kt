@file:OptIn(ExperimentalMaterialApi::class)

package com.zerogravity.moonlight.mobile.android.presentation.services

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.HomeFeedViewModel
import com.zerogravity.moonlight.mobile.common.domain.usecase.HomeFeedUiState
import org.koin.androidx.compose.koinViewModel


@Composable
fun ServiceListRoute(
    onBackClick: () -> Unit,
    viewModel: HomeFeedViewModel = koinViewModel()
) {
    val uiState by viewModel.homeFeedUiState.collectAsStateWithLifecycle()

    ServiceListScreen(uiState, onBackClick, onRefresh = {
//        viewModel.refresh()
    })

}

@Composable
fun ServiceListScreen(
    uiState: HomeFeedUiState,
    onBackClick: () -> Unit,
    onRefresh: () -> Unit
) {

}
