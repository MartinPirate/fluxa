package dev.fluxa.demo.screens

import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FeatureCard
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.NoticeCard
import dev.fluxa.ui.SectionHeader
import dev.fluxa.ui.button
import dev.fluxa.ui.divider
import dev.fluxa.ui.screen
import dev.fluxa.ui.toggle

fun settingsScreen(isDark: Boolean, theme: FluxaThemeTokens): FluxaNode = screen(
    SectionHeader(title = "Settings", theme = theme),
    FeatureCard(
        title = "Appearance",
        body = "Switch between Aurora light and dark themes.",
        theme = theme,
    ),
    toggle(
        label = "Dark mode",
        checked = isDark,
        style = style { padding(FluxaAxisScale.SM) },
    ),
    divider(style = FluxaStyles.divider(theme)),
    FeatureCard(
        title = "Data",
        body = "Manage cached notes and local storage.",
        theme = theme,
    ),
    button(
        label = "Clear cache",
        style = FluxaStyles.secondaryButton(theme),
    ),
    divider(style = FluxaStyles.divider(theme)),
    NoticeCard(
        title = "About",
        body = "Fluxa Notes v0.1.0 — a demo app exercising all Fluxa framework modules.",
        theme = theme,
    ),
)
