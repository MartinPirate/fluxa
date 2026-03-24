package dev.fluxa.demo.screens

import dev.fluxa.demo.store.Note
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FeatureCard
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.SectionHeader
import dev.fluxa.ui.StatusBadge
import dev.fluxa.ui.button
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.screen
import dev.fluxa.ui.spacer
import dev.fluxa.ui.text
import dev.fluxa.style.FluxaAlignment
import dev.fluxa.ui.onClick

fun noteDetailScreen(
    note: Note?,
    theme: FluxaThemeTokens,
    onDelete: (String) -> Unit = {},
    onBack: () -> Unit = {},
): FluxaNode {
    if (note == null) {
        return screen(
            FeatureCard(
                title = "Not found",
                body = "This note does not exist.",
                theme = theme,
            ),
        )
    }

    return screen(
        SectionHeader(title = note.category.label, theme = theme),
        column(
            style = style {
                width("full")
                padding(FluxaAxisScale.LG)
                gap(FluxaAxisScale.MD)
            },
            text(
                value = note.title,
                style = style {
                    foreground(theme.colors.textPrimary)
                    typography(theme.typography.title)
                },
            ),
            text(
                value = note.body,
                style = style {
                    foreground(theme.colors.textPrimary)
                    typography(theme.typography.body)
                },
            ),
        ),
        spacer(style = style { height("16") }),
        row(
            style = style {
                width("full")
                padding(FluxaAxisScale.MD)
                gap(FluxaAxisScale.SM)
                justifyContent(FluxaAlignment.END)
            },
            button(
                label = "Delete",
                style = FluxaStyles.secondaryButton(theme),
            ).onClick { onDelete(note.id) },
            button(
                label = "Back",
                style = FluxaStyles.primaryButton(theme),
            ).onClick { onBack() },
        ),
    )
}
