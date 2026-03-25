package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaThemeColor
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.stack
import dev.fluxa.ui.text

fun themeSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    // Color palette
    colorRow("Page", theme.colors.page, theme),
    colorRow("Panel", theme.colors.panel, theme),
    colorRow("Panel Accent", theme.colors.panelAccent, theme),
    colorRow("Panel Border", theme.colors.panelBorder, theme),
    colorRow("Text Primary", theme.colors.textPrimary, theme),
    colorRow("Text Secondary", theme.colors.textSecondary, theme),
    colorRow("Spotlight", theme.colors.spotlight, theme),
    colorRow("Pill", theme.colors.pill, theme),
    colorRow("Success", theme.colors.success, theme),
    colorRow("Warning", theme.colors.warning, theme),
    colorRow("Error", theme.colors.error, theme),
    colorRow("Divider", theme.colors.divider, theme),

    // Typography scale
    text("Typography Scale", style = style {
        foreground(theme.colors.textSecondary)
        typography(theme.typography.caption)
        paddingX(FluxaAxisScale.SM)
        paddingY(FluxaAxisScale.MD)
    }),
    text("Hero", style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.hero)
        paddingX(FluxaAxisScale.SM)
    }),
    text("Title", style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.title)
        paddingX(FluxaAxisScale.SM)
    }),
    text("Body", style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.body)
        paddingX(FluxaAxisScale.SM)
    }),
    text("Label", style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.label)
        paddingX(FluxaAxisScale.SM)
    }),
    text("Caption", style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.caption)
        paddingX(FluxaAxisScale.SM)
    }),
)

private fun colorRow(
    name: String,
    color: FluxaThemeColor,
    theme: FluxaThemeTokens,
): FluxaNode = row(
    style = style {
        width("full")
        padding(FluxaAxisScale.SM)
        gap(FluxaAxisScale.MD)
        alignItems(FluxaAlignment.CENTER)
    },
    // Color swatch
    stack(
        style = style {
            width("40")
            height("40")
            background(color)
            radius(FluxaRadiusScale.MD)
        },
    ),
    // Label
    column(
        style = style {
            gap(FluxaAxisScale.NONE)
        },
        text(name, style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.label)
        }),
        text(color.value, style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.caption)
        }),
    ),
)
