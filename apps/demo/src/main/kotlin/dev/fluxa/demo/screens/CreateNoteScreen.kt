package dev.fluxa.demo.screens

import dev.fluxa.demo.store.NoteCategory
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.FormGroup
import dev.fluxa.ui.InputField
import dev.fluxa.ui.ActionRow
import dev.fluxa.ui.PillRow
import dev.fluxa.ui.SectionHeader
import dev.fluxa.ui.button
import dev.fluxa.ui.screen

fun createNoteScreen(theme: FluxaThemeTokens): FluxaNode = screen(
    SectionHeader(title = "New Note", theme = theme),
    FormGroup(
        title = "Details",
        theme = theme,
        InputField(
            label = "Title",
            placeholder = "Note title",
            theme = theme,
        ),
        InputField(
            label = "Body",
            placeholder = "Write your note...",
            theme = theme,
        ),
    ),
    SectionHeader(title = "Category", theme = theme),
    PillRow(
        labels = NoteCategory.entries.map { it.label },
        theme = theme,
    ),
    ActionRow(
        theme = theme,
        button(
            label = "Cancel",
            style = FluxaStyles.secondaryButton(theme),
        ),
        button(
            label = "Save",
            style = FluxaStyles.primaryButton(theme),
        ),
    ),
)
