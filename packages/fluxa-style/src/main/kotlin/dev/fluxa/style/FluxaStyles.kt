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

        transition("background", FluxaDuration.NORMAL, FluxaEasing.EASE_IN_OUT)
        transition("opacity", FluxaDuration.FAST, FluxaEasing.EASE_OUT)

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

        transition("background", FluxaDuration.NORMAL, FluxaEasing.EASE_IN_OUT)
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

        transition("background", FluxaDuration.NORMAL, FluxaEasing.EASE_IN_OUT)

        responsive(FluxaBreakpoint.MEDIUM) {
            padding(FluxaAxisScale.XL)
        }

        responsive(FluxaBreakpoint.EXPANDED) {
            padding(FluxaAxisScale.XXL)
            gap(FluxaAxisScale.LG)
        }
    }

    fun featureCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.panel)
        foreground(theme.colors.textPrimary)
        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)
        typography(theme.typography.title)

        variant(FluxaVariant.PRESSED) {
            background(theme.colors.panelAccent)
            opacity(0.92f)
        }

        transition("background", FluxaDuration.NORMAL, FluxaEasing.EASE_IN_OUT)
        transition("opacity", FluxaDuration.FAST, FluxaEasing.EASE_OUT)

        responsive(FluxaBreakpoint.EXPANDED) {
            padding(FluxaAxisScale.XL)
            gap(FluxaAxisScale.LG)
        }
    }

    fun pillRow(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        gap(FluxaAxisScale.SM)
        justifyContent(FluxaAlignment.SPACE_BETWEEN)
        alignItems(FluxaAlignment.CENTER)

        responsive(FluxaBreakpoint.MEDIUM) {
            gap(FluxaAxisScale.MD)
        }
    }

    fun noticeCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.warning)
        foreground(theme.colors.warningText)
        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)
    }

    fun sectionHeader(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        foreground(theme.colors.textPrimary)
        paddingY(FluxaAxisScale.SM)
        typography(theme.typography.label)
        weight(FluxaWeight.SEMIBOLD)
    }

    fun textInput(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.inputBackground)
        foreground(theme.colors.textPrimary)
        padding(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.MD)
        border(theme.colors.inputBorder, FluxaBorderScale.THIN)
        typography(theme.typography.body)

        variant(FluxaVariant.DISABLED) {
            opacity(0.5f)
        }
    }

    fun primaryButton(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        background(theme.colors.spotlight)
        foreground(theme.colors.spotlightText)
        paddingX(FluxaAxisScale.XL)
        paddingY(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.MD)
        typography(theme.typography.label)
        weight(FluxaWeight.SEMIBOLD)
        alignItems(FluxaAlignment.CENTER)

        variant(FluxaVariant.PRESSED) {
            opacity(0.88f)
        }

        variant(FluxaVariant.DISABLED) {
            opacity(0.4f)
        }

        transition("opacity", FluxaDuration.FAST, FluxaEasing.EASE_OUT)
    }

    fun divider(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.divider)
        height("1")
    }

    fun secondaryButton(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        background(theme.colors.panel)
        foreground(theme.colors.textPrimary)
        paddingX(FluxaAxisScale.XL)
        paddingY(FluxaAxisScale.MD)
        radius(FluxaRadiusScale.MD)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)
        typography(theme.typography.label)
        weight(FluxaWeight.MEDIUM)
        alignItems(FluxaAlignment.CENTER)

        variant(FluxaVariant.PRESSED) {
            background(theme.colors.panelAccent)
        }

        variant(FluxaVariant.DISABLED) {
            opacity(0.4f)
        }

        transition("background", FluxaDuration.NORMAL, FluxaEasing.EASE_IN_OUT)
        transition("opacity", FluxaDuration.FAST, FluxaEasing.EASE_OUT)
    }

    fun imageCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.panel)
        radius(FluxaRadiusScale.LG)
        shadow(FluxaAxisScale.SM)
    }

    fun avatar(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("40")
        height("40")
        background(theme.colors.panelAccent)
        radius(FluxaRadiusScale.PILL)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.CENTER)
    }

    fun errorCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.error)
        foreground(theme.colors.errorText)
        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.errorText, FluxaBorderScale.THIN)
    }

    fun successCard(theme: FluxaThemeTokens = FluxaThemes.Aurora): FluxaStyle = style {
        width("full")
        background(theme.colors.success)
        foreground(theme.colors.successText)
        padding(FluxaAxisScale.LG)
        gap(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.LG)
    }
}
