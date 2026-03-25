package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.ui.EmptyState
import dev.fluxa.ui.ErrorState
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.LoadingIndicator
import dev.fluxa.ui.SuccessState
import dev.fluxa.ui.button

fun stateSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    LoadingIndicator(
        message = "Fetching data…",
        theme = theme,
    ),

    EmptyState(
        title = "No results",
        message = "Try adjusting your search or create something new.",
        theme = theme,
        action = button(
            label = "Create New",
            style = FluxaStyles.primaryButton(theme),
        ),
    ),

    ErrorState(
        title = "Connection failed",
        message = "Could not reach the server. Check your internet connection and try again.",
        theme = theme,
        onRetry = { /* showcase — no-op */ },
    ),

    ErrorState(
        title = "Not found",
        message = "The requested resource does not exist.",
        theme = theme,
    ),

    SuccessState(
        title = "Saved successfully",
        message = "Your changes have been applied.",
        theme = theme,
    ),
)
