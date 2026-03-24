package dev.fluxa.nav

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

val LocalFluxaNav = staticCompositionLocalOf<FluxaBackStack> {
    error("No FluxaBackStack provided. Wrap your content in FluxaRouter.")
}

/**
 * Top-level navigation host. Renders the current route's screen
 * with animated transitions.
 */
@Composable
fun FluxaRouter(
    backStack: FluxaBackStack,
    content: @Composable (FluxaRoute) -> Unit,
) {
    var currentRoute by remember { mutableStateOf(backStack.current) }
    var previousSize by remember { mutableStateOf(backStack.entries.size) }

    DisposableEffect(backStack) {
        val cancel = backStack.observe { entries ->
            val newSize = entries.size
            currentRoute = entries.last()
            previousSize = newSize
        }
        onDispose { cancel() }
    }

    CompositionLocalProvider(LocalFluxaNav provides backStack) {
        AnimatedContent(
            targetState = currentRoute,
            transitionSpec = {
                if (backStack.entries.size >= previousSize) {
                    (slideInHorizontally { it } + fadeIn()) togetherWith
                        (slideOutHorizontally { -it / 3 } + fadeOut())
                } else {
                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                        (slideOutHorizontally { it / 3 } + fadeOut())
                }
            },
            label = "FluxaRouter",
        ) { route ->
            content(route)
        }
    }
}

/**
 * Navigate from within a composable.
 */
@Composable
fun rememberFluxaNav(): FluxaBackStack = LocalFluxaNav.current
