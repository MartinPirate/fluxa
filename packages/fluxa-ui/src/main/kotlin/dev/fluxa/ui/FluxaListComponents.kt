package dev.fluxa.ui

import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaBorderScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.style

fun ListItem(
    title: String,
    subtitle: String = "",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    trailing: FluxaNode? = null,
    leading: FluxaNode? = null,
): FluxaNode = row(
    style = style {
        width("full")
        padding(FluxaAxisScale.MD)
        gap(FluxaAxisScale.MD)
        alignItems(FluxaAlignment.CENTER)
        justifyContent(FluxaAlignment.SPACE_BETWEEN)
        border(theme.colors.divider, FluxaBorderScale.THIN)
        radius(FluxaRadiusScale.SM)
    },
    *buildList {
        leading?.let { add(it) }
        add(column(
            style = style {
                gap(FluxaAxisScale.XS)
            },
            *buildList {
                add(text(
                    value = title,
                    style = style {
                        foreground(theme.colors.textPrimary)
                        typography(theme.typography.body)
                    },
                ))
                if (subtitle.isNotBlank()) {
                    add(text(
                        value = subtitle,
                        style = style {
                            foreground(theme.colors.textSecondary)
                            typography(theme.typography.caption)
                        },
                    ))
                }
            }.toTypedArray(),
        ))
        trailing?.let { add(it) }
    }.toTypedArray(),
)

fun CardGrid(
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    vararg children: FluxaNode,
): FluxaNode = lazyRow(
    style = style {
        width("full")
        gap(FluxaAxisScale.MD)
        padding(FluxaAxisScale.SM)
    },
    *children,
)

fun ScrollableList(
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    style: FluxaStyle = style {
        width("full")
        gap(FluxaAxisScale.XS)
    },
    vararg children: FluxaNode,
): FluxaNode = lazyColumn(
    style = style,
    *children,
)
