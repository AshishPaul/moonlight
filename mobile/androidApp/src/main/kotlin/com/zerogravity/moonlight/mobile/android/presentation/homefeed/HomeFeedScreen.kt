@file:OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.zerogravity.moonlight.mobile.android.presentation.homefeed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.R
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.TopAppBar
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.usecase.CategoriesUiState
import com.zerogravity.moonlight.mobile.common.domain.usecase.HomeFeedUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.HomeFeedUiState
import com.zerogravity.moonlight.shared.domain.model.dto.Category
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.get


const val HomeFeedTag = "HomeFeed"

internal fun NavGraphBuilder.homeFeedRoute(
    modifier: Modifier = Modifier,

    onCategoryClick: (String) -> Unit
) {
    composable(route = HomeFeedDestination.route) {
        val viewModel: HomeFeedViewModel = koinViewModel()
        val appLogger: AppLogger = get(AppLogger::class.java)
        appLogger.d("HomeFeedRoute: Start HomeFeedRoute")
        val uiState by viewModel.homeFeedUiState.collectAsStateWithLifecycle()
        appLogger.d("HomeFeedRoute: uiState : $uiState")

        HomeFeedScreen(uiState, modifier, onCategoryClick = onCategoryClick, onUiEvent = {
            viewModel.onHomeFeedUiEvent(it)
        })
    }
}

@ExperimentalMaterialApi
@Composable
fun HomeFeedScreen(
    homeUiState: HomeFeedUiState,
    modifier: Modifier = Modifier,
    appLogger: AppLogger = get(AppLogger::class.java),
    onCategoryClick: (String) -> Unit,
    onUiEvent: (HomeFeedUiEvent) -> Unit
) {
    appLogger.d("HomeFeedScreen: Entry to HomeFeedScreen")
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val pullRefreshState = rememberPullRefreshState(
        homeUiState.isRefreshing,
        refreshThreshold = 120.dp,
        onRefresh = { onUiEvent.invoke(HomeFeedUiEvent.RefreshData) }
    )

    val appName = stringResource(id = R.string.app_name)

    if (homeUiState.appError != null) {
        val errorMessage = homeUiState.appError!!.errorMessage
        LaunchedEffect(errorMessage) {
            snackBarHostState.showSnackbar(message = errorMessage)
            onUiEvent.invoke(HomeFeedUiEvent.ErrorConsumed)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(titleRes = R.string.app_name,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.semantics { contentDescription = appName })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .pullRefresh(pullRefreshState)
                .wrapContentSize(Alignment.Center)

        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val categoryState = homeUiState.categoriesState) {
                    is CategoriesUiState.Empty -> {
                        if (!homeUiState.isRefreshing) {
                            HomeFeedEmptyView(homeUiState) {
                                onUiEvent(HomeFeedUiEvent.RefreshData)
                            }
                        }
                    }

                    is CategoriesUiState.Populated -> {
                        CategoryList(innerPadding, categoryState.categories) {
                            onCategoryClick(it)
                        }
                    }
                }
            }
            PullRefreshIndicator(
                homeUiState.isRefreshing, pullRefreshState, modifier = modifier
            )
        }
    }
}

@Composable
fun HomeFeedEmptyView(
    homeUiState: HomeFeedUiState,
    onRetryClick: () -> Unit
) {
    Text(
        text = if (homeUiState.appError != null) {
            homeUiState.appError!!.errorMessage
        } else {
            "No data found"
        },
        modifier = Modifier.padding(0.dp, 16.dp)
    )

    OutlinedButton(onClick = onRetryClick) {
        Text(text = "Retry")
    }
}

@Composable
fun CategoryList(
    innerPadding: PaddingValues,
    categories: List<Category>,
    onListItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .testTag(HomeFeedTag)
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .fillMaxSize()
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        }
        categoryCardItems(categories, onListItemClick = onListItemClick)
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

fun LazyListScope.categoryCardItems(
    items: List<Category>,
    itemModifier: Modifier = Modifier,
    onListItemClick: (String) -> Unit,
) = items(
    items = items,
    key = { it.id },
    itemContent = { category ->
        CategoryCard(
            category = category,
            onClick = {
                onListItemClick.invoke(category.id)
            },
            modifier = itemModifier.fillMaxSize(),
        )
    },
)

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val clickActionLabel = "Tap on Category"

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        // Use custom label for accessibility services to communicate button's action to user.
        // Pass null for action to only override the label and not the actual action.
        modifier = modifier.semantics {
            onClick(label = clickActionLabel, action = null)
        },
    ) {
        Column {
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        category.title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = modifier
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(category.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}