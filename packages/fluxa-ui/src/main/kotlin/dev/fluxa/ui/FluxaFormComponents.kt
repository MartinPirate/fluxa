package dev.fluxa.ui

import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.style

fun InputField(
    label: String,
    placeholder: String = "",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    enabled: Boolean = true,
): FluxaNode = column(
    style = style {
        width("full")
        gap(FluxaAxisScale.XS)
    },
    text(
        value = label,
        style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.label)
        },
    ),
    textField(
        placeholder = placeholder,
        style = FluxaStyles.textInput(theme),
        enabled = enabled,
    ),
)

fun FormGroup(
    title: String = "",
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    vararg children: FluxaNode,
): FluxaNode = column(
    style = style {
        width("full")
        gap(FluxaAxisScale.MD)
        padding(FluxaAxisScale.LG)
        background(theme.colors.panel)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder)
    },
    *buildList {
        if (title.isNotBlank()) {
            add(text(
                value = title,
                style = style {
                    foreground(theme.colors.textPrimary)
                    typography(theme.typography.title)
                },
            ))
        }
        addAll(children.toList())
    }.toTypedArray(),
)

fun ActionRow(
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    vararg children: FluxaNode,
): FluxaNode = row(
    style = style {
        width("full")
        gap(FluxaAxisScale.SM)
        justifyContent(FluxaAlignment.END)
        alignItems(FluxaAlignment.CENTER)
    },
    *children,
)

fun ErrorText(
    message: String,
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
): FluxaNode = text(
    value = message,
    style = style {
        foreground(theme.colors.errorText)
        typography(theme.typography.caption)
    },
)
