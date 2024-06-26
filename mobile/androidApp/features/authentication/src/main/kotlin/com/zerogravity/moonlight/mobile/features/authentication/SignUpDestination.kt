package com.zerogravity.moonlight.mobile.features.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination

object SignUpDestination : TopLevelDestination {
    override val route = "signup_route"
    override val graphRoute = "signup_graph_route"
}

fun NavGraphBuilder.signUpGraph(onSignUpSuccess: () -> Unit) {

    navigation(
        startDestination = SignUpDestination.route,
        route = SignUpDestination.graphRoute,
//        deepLinks = listOf(
//            navDeepLink { uriPattern = REGISTRATION_DEEPLINK }
//        )
    ) {
        signUpRoute(onSignUpSuccess = onSignUpSuccess)
    }


}
