package dev.fluxa.ui

import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.style

fun HeroPanel(
    title: String,
    subtitle: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    style: FluxaStyle = FluxaStyles.heroPanel(theme),
): FluxaNode = column(
    style = style,
    text(title),
    text(subtitle),
)

fun StatusBadge(
    label: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    style: FluxaStyle = FluxaStyles.statusBadge(theme),
): FluxaNode = text(
    value = label,
    style = style,
)

fun SpotlightCard(
    title: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    style: FluxaStyle = FluxaStyles.spotlightCard(theme),
    vararg children: FluxaNode,
): FluxaNode = stack(
    style = style,
    text(title),
    *children,
)

fun SectionCard(
    title: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    style: FluxaStyle = FluxaStyles.adaptiveFeatureCard(theme),
    headerTrailing: FluxaNode? = null,
    vararg children: FluxaNode,
): FluxaNode {
    val header = row(
        style = style {
            width("full")
            justifyContent(FluxaAlignment.SPACE_BETWEEN)
            alignItems(FluxaAlignment.CENTER)
        },
        text(title),
        *(listOfNotNull(headerTrailing).toTypedArray()),
    )

    return column(
        style = style,
        header,
        *children,
    )
}

fun SelfAlignedPill(
    label: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
): FluxaNode = text(
    value = label,
    style = style {
        background(theme.colors.pill)
        foreground(theme.colors.textPrimary)
        paddingX(FluxaAxisScale.MD)
        paddingY(FluxaAxisScale.XS)
        radius(FluxaRadiusScale.PILL)
        alignSelf(FluxaAlignment.END)
    },
)

fun SelectableNotice(
    title: String,
    body: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    selected: Boolean = true,
): FluxaNode {
    val node = column(
        style = FluxaStyles.selectedNotice(theme),
        text(title),
        text(body),
    )

    return if (selected) node.withVariants(FluxaVariant.SELECTED) else node
}
