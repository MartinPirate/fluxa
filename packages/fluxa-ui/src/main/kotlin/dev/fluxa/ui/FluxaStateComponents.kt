package dev.fluxa.ui

import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.style

/**
 * A centered loading indicator with optional message.
 */
fun LoadingIndicator(
    message: String = "Loading…",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
): FluxaNode = column(
    style = style {
        width("full")
        padding(FluxaAxisScale.XXL)
        gap(FluxaAxisScale.MD)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.CENTER)
    },
    // Spinner dot row — three dots the renderer can animate
    row(
        style = style {
            gap(FluxaAxisScale.SM)
            alignItems(FluxaAlignment.CENTER)
        },
        spinnerDot(theme, 0),
        spinnerDot(theme, 1),
        spinnerDot(theme, 2),
    ),
    text(
        value = message,
        style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.body)
        },
    ),
)

private fun spinnerDot(theme: FluxaThemeTokens, index: Int): FluxaNode = text(
    value = "●",
    style = style {
        foreground(theme.colors.spotlight)
        typography(theme.typography.title)
    },
).let { node ->
    node.copy(meta = node.meta + ("spinnerIndex" to index.toString()))
}

/**
 * Empty state placeholder with icon area, title, and message.
 */
fun EmptyState(
    title: String = "Nothing here",
    message: String = "",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    action: FluxaNode? = null,
): FluxaNode = column(
    style = style {
        width("full")
        padding(FluxaAxisScale.XXL)
        gap(FluxaAxisScale.MD)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.CENTER)
    },
    // Icon placeholder
    stack(
        style = style {
            width("64")
            height("64")
            background(theme.colors.panel)
            radius(FluxaRadiusScale.PILL)
            alignItems(FluxaAlignment.CENTER)
            justifyContent(FluxaAlignment.CENTER)
        },
        text(
            value = "∅",
            style = style {
                foreground(theme.colors.textSecondary)
                typography(theme.typography.hero)
            },
        ),
    ),
    text(
        value = title,
        style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.title)
        },
    ),
    *buildList {
        if (message.isNotBlank()) {
            add(text(
                value = message,
                style = style {
                    foreground(theme.colors.textSecondary)
                    typography(theme.typography.body)
                    alignSelf(FluxaAlignment.CENTER)
                },
            ))
        }
        if (action != null) add(action)
    }.toTypedArray(),
)

/**
 * Error state with message and optional retry action.
 */
fun ErrorState(
    title: String = "Something went wrong",
    message: String = "",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    onRetry: (() -> Unit)? = null,
): FluxaNode = column(
    style = style {
        width("full")
        background(theme.colors.error)
        padding(FluxaAxisScale.XL)
        gap(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.LG)
        alignItems(FluxaAlignment.CENTER)
    },
    // Error icon
    stack(
        style = style {
            width("48")
            height("48")
            background(theme.colors.errorText)
            radius(FluxaRadiusScale.PILL)
            alignItems(FluxaAlignment.CENTER)
            justifyContent(FluxaAlignment.CENTER)
        },
        text(
            value = "!",
            style = style {
                foreground(theme.colors.error)
                typography(theme.typography.title)
            },
        ),
    ),
    text(
        value = title,
        style = style {
            foreground(theme.colors.errorText)
            typography(theme.typography.title)
        },
    ),
    *buildList {
        if (message.isNotBlank()) {
            add(text(
                value = message,
                style = style {
                    foreground(theme.colors.errorText)
                    typography(theme.typography.body)
                },
            ))
        }
        if (onRetry != null) {
            add(
                button(
                    label = "Retry",
                    style = FluxaStyles.primaryButton(theme),
                ).onClick { onRetry() }
            )
        }
    }.toTypedArray(),
)

/**
 * Success confirmation state with message.
 */
fun SuccessState(
    title: String = "Done",
    message: String = "",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
): FluxaNode = column(
    style = style {
        width("full")
        background(theme.colors.success)
        padding(FluxaAxisScale.XL)
        gap(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.LG)
        alignItems(FluxaAlignment.CENTER)
    },
    // Checkmark icon
    stack(
        style = style {
            width("48")
            height("48")
            background(theme.colors.successText)
            radius(FluxaRadiusScale.PILL)
            alignItems(FluxaAlignment.CENTER)
            justifyContent(FluxaAlignment.CENTER)
        },
        text(
            value = "✓",
            style = style {
                foreground(theme.colors.success)
                typography(theme.typography.title)
            },
        ),
    ),
    text(
        value = title,
        style = style {
            foreground(theme.colors.successText)
            typography(theme.typography.title)
        },
    ),
    *buildList {
        if (message.isNotBlank()) {
            add(text(
                value = message,
                style = style {
                    foreground(theme.colors.successText)
                    typography(theme.typography.body)
                },
            ))
        }
    }.toTypedArray(),
)
