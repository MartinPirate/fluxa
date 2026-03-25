package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.stack
import dev.fluxa.ui.text

fun layoutSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    // Column demo
    column(
        style = style {
            width("full")
            padding(FluxaAxisScale.MD)
            gap(FluxaAxisScale.SM)
            background(theme.colors.panel)
            radius(FluxaRadiusScale.LG)
        },
        text("column()", style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.label)
        }),
        colorBlock("Child 1", theme),
        colorBlock("Child 2", theme),
        colorBlock("Child 3", theme),
    ),

    // Row demo
    row(
        style = style {
            width("full")
            padding(FluxaAxisScale.MD)
            gap(FluxaAxisScale.SM)
            background(theme.colors.panel)
            radius(FluxaRadiusScale.LG)
        },
        text("row()", style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.label)
        }),
        colorBlock("A", theme),
        colorBlock("B", theme),
        colorBlock("C", theme),
    ),

    // Stack demo
    stack(
        style = style {
            width("full")
            height("120")
            padding(FluxaAxisScale.MD)
            background(theme.colors.panel)
            radius(FluxaRadiusScale.LG)
            alignItems(FluxaAlignment.CENTER)
            justifyContent(FluxaAlignment.CENTER)
        },
        text("stack() — children overlap", style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.body)
        }),
    ),

    // Alignment demo
    column(
        style = style {
            width("full")
            padding(FluxaAxisScale.MD)
            gap(FluxaAxisScale.SM)
            background(theme.colors.panel)
            radius(FluxaRadiusScale.LG)
            alignItems(FluxaAlignment.CENTER)
        },
        text("alignItems: center", style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.label)
        }),
        colorBlock("Centered", theme),
    ),

    // Space between demo
    row(
        style = style {
            width("full")
            padding(FluxaAxisScale.MD)
            background(theme.colors.panel)
            radius(FluxaRadiusScale.LG)
            justifyContent(FluxaAlignment.SPACE_BETWEEN)
            alignItems(FluxaAlignment.CENTER)
        },
        text("justifyContent: space_between", style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.label)
        }),
        colorBlock("End", theme),
    ),
)

private fun colorBlock(label: String, theme: FluxaThemeTokens): FluxaNode = stack(
    style = style {
        background(theme.colors.panelAccent)
        paddingX(FluxaAxisScale.MD)
        paddingY(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.MD)
    },
    text(label, style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.caption)
    }),
)
