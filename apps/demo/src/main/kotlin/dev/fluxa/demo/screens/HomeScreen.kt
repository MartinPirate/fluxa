package dev.fluxa.demo.screens

import dev.fluxa.demo.store.Note
import dev.fluxa.demo.store.NoteCategory
import dev.fluxa.demo.store.NoteState
import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaBorderScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.HeroPanel
import dev.fluxa.ui.PillRow
import dev.fluxa.ui.SectionHeader
import dev.fluxa.ui.StatusBadge
import dev.fluxa.ui.button
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.screen
import dev.fluxa.ui.text
import dev.fluxa.ui.onClick
import dev.fluxa.ui.textField
import dev.fluxa.ui.withVariants

fun homeScreen(
    state: NoteState,
    theme: FluxaThemeTokens,
    onNoteClick: (String) -> Unit = {},
    onNewNote: () -> Unit = {},
): FluxaNode = screen(
    HeroPanel(
        title = "Fluxa Notes",
        subtitle = "${state.filteredNotes.size} notes",
        theme = theme,
    ),
    PillRow(
        labels = listOf("All") + NoteCategory.entries.map { it.label },
        theme = theme,
    ),
    textField(
        placeholder = "Search notes...",
        style = FluxaStyles.textInput(theme),
    ),
    SectionHeader(title = "Recent", theme = theme),
    *state.filteredNotes.map { note ->
        noteCard(note, theme).onClick { onNoteClick(note.id) }
    }.toTypedArray(),
    button(
        label = "New Note",
        style = FluxaStyles.primaryButton(theme),
    ).onClick { onNewNote() },
)

private fun noteCard(note: Note, theme: FluxaThemeTokens): FluxaNode = column(
    style = style {
        width("full")
        background(theme.colors.panel)
        foreground(theme.colors.textPrimary)
        padding(FluxaAxisScale.MD)
        gap(FluxaAxisScale.SM)
        radius(FluxaRadiusScale.LG)
        border(theme.colors.panelBorder, FluxaBorderScale.THIN)
    },
    row(
        style = style {
            width("full")
            justifyContent(FluxaAlignment.SPACE_BETWEEN)
            alignItems(FluxaAlignment.CENTER)
        },
        text(
            value = note.title,
            style = style {
                foreground(theme.colors.textPrimary)
                typography(theme.typography.title)
            },
        ),
        StatusBadge(label = note.category.label, theme = theme),
    ),
    text(
        value = note.body.take(80) + if (note.body.length > 80) "..." else "",
        style = style {
            foreground(theme.colors.textSecondary)
            typography(theme.typography.body)
        },
    ),
)
