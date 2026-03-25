package dev.fluxa.compose

import androidx.compose.runtime.Composable
import dev.fluxa.data.FluxaResource
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.ui.EmptyState
import dev.fluxa.ui.ErrorState
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.LoadingIndicator

/**
 * Maps a [FluxaResource] to the appropriate FluxaNode tree and renders it.
 *
 * Usage:
 * ```
 * FluxaResourceView(
 *     resource = noteResource,
 *     theme = theme,
 *     onRetry = { reload() },
 * ) { notes ->
 *     noteListScreen(notes, theme)
 * }
 * ```
 */
@Composable
fun <T> FluxaResourceView(
    resource: FluxaResource<T>,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    context: FluxaRenderContext = FluxaRenderContext(),
    loadingMessage: String = "Loading…",
    emptyTitle: String = "Nothing here",
    emptyMessage: String = "",
    errorTitle: String = "Something went wrong",
    onRetry: (() -> Unit)? = null,
    content: (T) -> FluxaNode,
) {
    val node = when (resource) {
        is FluxaResource.Idle -> LoadingIndicator(message = loadingMessage, theme = theme)
        is FluxaResource.Loading -> LoadingIndicator(message = loadingMessage, theme = theme)
        is FluxaResource.Error -> ErrorState(
            title = errorTitle,
            message = resource.message,
            theme = theme,
            onRetry = onRetry,
        )
        is FluxaResource.Success -> content(resource.data)
    }

    RenderFluxaNode(node = node, context = context)
}

/**
 * Pure function version — returns a FluxaNode instead of rendering directly.
 * Useful when you need to compose the result into a larger tree.
 */
fun <T> resourceNode(
    resource: FluxaResource<T>,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    loadingMessage: String = "Loading…",
    emptyTitle: String = "Nothing here",
    emptyMessage: String = "",
    errorTitle: String = "Something went wrong",
    onRetry: (() -> Unit)? = null,
    content: (T) -> FluxaNode,
): FluxaNode = when (resource) {
    is FluxaResource.Idle -> LoadingIndicator(message = loadingMessage, theme = theme)
    is FluxaResource.Loading -> LoadingIndicator(message = loadingMessage, theme = theme)
    is FluxaResource.Error -> ErrorState(
        title = errorTitle,
        message = resource.message,
        theme = theme,
        onRetry = onRetry,
    )
    is FluxaResource.Success -> content(resource.data)
}
