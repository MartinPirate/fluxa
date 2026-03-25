package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.button
import dev.fluxa.ui.checkbox
import dev.fluxa.ui.divider
import dev.fluxa.ui.spacer
import dev.fluxa.ui.textField
import dev.fluxa.ui.toggle

fun inputsSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    textField(
        label = "Text Field",
        placeholder = "Type something...",
        style = FluxaStyles.textInput(theme),
    ),

    textField(
        label = "Pre-filled",
        placeholder = "Controlled value",
        value = "Hello Fluxa",
        style = FluxaStyles.textInput(theme),
    ),

    textField(
        label = "Disabled Field",
        placeholder = "Cannot edit",
        style = FluxaStyles.textInput(theme),
        enabled = false,
    ),

    toggle(label = "Toggle (off)", style = style {
        padding(FluxaAxisScale.SM)
    }),

    toggle(label = "Toggle (on)", checked = true, style = style {
        padding(FluxaAxisScale.SM)
    }),

    checkbox(label = "Checkbox (unchecked)", style = style {
        padding(FluxaAxisScale.SM)
    }),

    checkbox(label = "Checkbox (checked)", checked = true, style = style {
        padding(FluxaAxisScale.SM)
    }),

    button(
        label = "Primary Button",
        style = FluxaStyles.primaryButton(theme),
    ),

    button(
        label = "Secondary Button",
        style = FluxaStyles.secondaryButton(theme),
    ),

    button(
        label = "Disabled Button",
        style = FluxaStyles.primaryButton(theme),
        enabled = false,
    ),

    divider(style = FluxaStyles.divider(theme)),

    spacer(style = style { height("24") }),
)
