package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.ui.ActionRow
import dev.fluxa.ui.ErrorText
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.FormGroup
import dev.fluxa.ui.InputField
import dev.fluxa.ui.button
import dev.fluxa.ui.checkbox
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.style

fun formsSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    FormGroup(
        title = "Login",
        theme = theme,
        InputField(
            label = "Email",
            placeholder = "you@example.com",
            theme = theme,
        ),
        InputField(
            label = "Password",
            placeholder = "Enter password",
            theme = theme,
        ),
        checkbox(label = "Remember me", style = style {
            padding(FluxaAxisScale.XS)
        }),
        ErrorText(message = "Invalid credentials", theme = theme),
        ActionRow(
            theme = theme,
            button(label = "Cancel", style = FluxaStyles.secondaryButton(theme)),
            button(label = "Sign In", style = FluxaStyles.primaryButton(theme)),
        ),
    ),

    FormGroup(
        title = "Profile",
        theme = theme,
        InputField(
            label = "Display Name",
            placeholder = "Your name",
            value = "Martin",
            theme = theme,
        ),
        InputField(
            label = "Bio",
            placeholder = "Tell us about yourself",
            theme = theme,
        ),
        ActionRow(
            theme = theme,
            button(label = "Save", style = FluxaStyles.primaryButton(theme)),
        ),
    ),
)
