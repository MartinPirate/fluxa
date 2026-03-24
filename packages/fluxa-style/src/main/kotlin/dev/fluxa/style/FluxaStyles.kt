package dev.fluxa.style

import dev.fluxa.runtime.FluxaBreakpoint

object FluxaStyles {
    fun surfaceCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        theme(theme)

        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.MD)
        background(theme.colors.panel)
        foreground(theme.colors.textPrimary)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder)
        typography(theme.typography.title)

        variant(FluxaVariant.PRESSED) {
            background(theme.colors.panelAccent)
            opacity(0.92f)
        }

        responsive(FluxaBreakpoint.EXPANDED) {
            padding(FluxaAxisScale.XL)
            gap(FluxaAxisScale.LG)
        }
    }

    fun heroPanel(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.page)
        foreground(theme.colors.textPrimary)
        padding(FluxaAxisScale.XL)
        gap(FluxaAxisScale.LG)
        typography(theme.typography.hero)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)
    }

    fun pill(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        background(theme.colors.pill)
        foreground(theme.colors.pillText)
        paddingX(FluxaAxisScale.LG)
        paddingY(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.PILL)
        alignItems(FluxaAlignment.CENTER)
        shadow(FluxaAxisScale.XS)
    }

    fun spotlightCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.spotlight)
        foreground(theme.colors.spotlightText)
        padding(FluxaAxisScale.XL)
        radius(FluxaRadiusScale.XL)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.CENTER)
        shadow(FluxaAxisScale.MD)
    }

    fun statusBadge(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        background(theme.colors.success)
        foreground(theme.colors.successText)
        paddingX(FluxaAxisScale.MD)
        paddingY(FluxaAxisScale.XS)
        radius(FluxaRadiusScale.PILL)
        typography(theme.typography.label)
    }

    fun selectedNotice(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.page)
        foreground(theme.colors.warningText)
        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)

        variant(FluxaVariant.SELECTED) {
            background(theme.colors.warning)
        }
    }

    fun adaptiveFeatureCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.panel)
        foreground(theme.colors.textPrimary)
        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)
        shadow(FluxaAxisScale.SM)

        variant(FluxaVariant.EMPHASIS) {
            background(theme.colors.panelAccent)
        }

        responsive(FluxaBreakpoint.MEDIUM) {
            padding(FluxaAxisScale.XL)
        }

        responsive(FluxaBreakpoint.EXPANDED) {
            padding(FluxaAxisScale.XXL)
            gap(FluxaAxisScale.LG)
        }
    }
}
