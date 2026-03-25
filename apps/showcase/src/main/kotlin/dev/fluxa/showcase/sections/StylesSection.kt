package dev.fluxa.showcase.sections

import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaDuration
import dev.fluxa.style.FluxaEasing
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.stack
import dev.fluxa.ui.text
import dev.fluxa.ui.withVariants

fun stylesSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    // Variant showcase
    sectionLabel("Variants", theme),
    row(
        style = style {
            width("full")
            gap(FluxaAxisScale.SM)
            padding(FluxaAxisScale.SM)
        },
        variantChip("Default", theme, false),
        variantChip("Emphasis", theme, true),
    ),

    // Radius scale
    sectionLabel("Radius Scale", theme),
    row(
        style = style {
            width("full")
            gap(FluxaAxisScale.SM)
            padding(FluxaAxisScale.SM)
            alignItems(FluxaAlignment.CENTER)
        },
        radiusDemo("SM", 4, theme),
        radiusDemo("MD", 8, theme),
        radiusDemo("LG", 16, theme),
        radiusDemo("XL", 24, theme),
        radiusDemo("Pill", 999, theme),
    ),

    // Shadow scale
    sectionLabel("Shadow Scale", theme),
    row(
        style = style {
            width("full")
            gap(FluxaAxisScale.MD)
            padding(FluxaAxisScale.MD)
            alignItems(FluxaAlignment.CENTER)
        },
        shadowDemo("XS", 1, theme),
        shadowDemo("SM", 2, theme),
        shadowDemo("MD", 4, theme),
        shadowDemo("LG", 6, theme),
    ),

    // Spacing scale
    sectionLabel("Padding Scale", theme),
    column(
        style = style {
            width("full")
            gap(FluxaAxisScale.SM)
            padding(FluxaAxisScale.SM)
        },
        paddingDemo("XS (1)", 1, theme),
        paddingDemo("SM (2)", 2, theme),
        paddingDemo("MD (4)", 4, theme),
        paddingDemo("LG (6)", 6, theme),
        paddingDemo("XL (8)", 8, theme),
    ),

    // Transition demo
    sectionLabel("Animated Transitions", theme),
    column(
        style = style {
            width("full")
            background(theme.colors.panel)
            padding(FluxaAxisScale.LG)
            radius(FluxaRadiusScale.LG)
            gap(FluxaAxisScale.SM)

            variant(FluxaVariant.EMPHASIS) {
                background(theme.colors.panelAccent)
            }

            transition("background", FluxaDuration.SLOW, FluxaEasing.EASE_IN_OUT)
        },
        text("This card has transition(\"background\", SLOW)", style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.body)
        }),
        text("Toggle EMPHASIS variant to see the animated color change.", style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.caption)
        }),
    ).withVariants(FluxaVariant.EMPHASIS),

    // Responsive demo
    sectionLabel("Responsive Breakpoints", theme),
    column(
        style = style {
            width("full")
            background(theme.colors.panel)
            padding(FluxaAxisScale.MD)
            radius(FluxaRadiusScale.LG)
            gap(FluxaAxisScale.SM)

            responsive(FluxaBreakpoint.MEDIUM) {
                padding(FluxaAxisScale.LG)
                background(theme.colors.panelAccent)
            }

            responsive(FluxaBreakpoint.EXPANDED) {
                padding(FluxaAxisScale.XXL)
            }
        },
        text("Resize to see responsive rules", style = style {
            foreground(theme.colors.textPrimary)
            typography(theme.typography.body)
        }),
        text("COMPACT: MD padding • MEDIUM: LG + accent • EXPANDED: XXL", style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.caption)
        }),
    ),
)

private fun sectionLabel(label: String, theme: FluxaThemeTokens): FluxaNode = text(
    value = label,
    style = style {
        foreground(theme.colors.textSecondary)
        typography(theme.typography.caption)
        paddingX(FluxaAxisScale.SM)
        paddingY(FluxaAxisScale.XS)
    },
)

private fun variantChip(label: String, theme: FluxaThemeTokens, emphasis: Boolean): FluxaNode {
    val node = stack(
        style = style {
            background(theme.colors.panel)
            foreground(theme.colors.textPrimary)
            paddingX(FluxaAxisScale.LG)
            paddingY(FluxaAxisScale.MD)
            radius(FluxaRadiusScale.LG)

            variant(FluxaVariant.EMPHASIS) {
                background(theme.colors.panelAccent)
            }

            transition("background", FluxaDuration.NORMAL, FluxaEasing.EASE_IN_OUT)
        },
        text(label),
    )
    return if (emphasis) node.withVariants(FluxaVariant.EMPHASIS) else node
}

private fun radiusDemo(label: String, dp: Int, theme: FluxaThemeTokens): FluxaNode = stack(
    style = style {
        width("48")
        height("48")
        background(theme.colors.panelAccent)
        radius(FluxaRadiusScale.entries.firstOrNull { it.step == dp } ?: FluxaRadiusScale.MD)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.CENTER)
    },
    text(label, style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.caption)
    }),
)

private fun shadowDemo(label: String, dp: Int, theme: FluxaThemeTokens): FluxaNode = stack(
    style = style {
        width("56")
        height("56")
        background(theme.colors.page)
        radius(FluxaRadiusScale.MD)
        shadow(FluxaAxisScale.entries.firstOrNull { it.step == dp } ?: FluxaAxisScale.SM)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.CENTER)
    },
    text(label, style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.caption)
    }),
)

private fun paddingDemo(label: String, step: Int, theme: FluxaThemeTokens): FluxaNode = stack(
    style = style {
        width("full")
        background(theme.colors.panelAccent)
        padding(FluxaAxisScale.entries.firstOrNull { it.step == step } ?: FluxaAxisScale.MD)
        radius(FluxaRadiusScale.SM)
    },
    text(label, style = style {
        foreground(theme.colors.textPrimary)
        typography(theme.typography.caption)
    }),
)
