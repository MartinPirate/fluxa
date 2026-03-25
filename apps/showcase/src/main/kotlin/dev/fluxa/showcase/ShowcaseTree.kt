package dev.fluxa.showcase

import dev.fluxa.showcase.sections.cardsSection
import dev.fluxa.showcase.sections.formsSection
import dev.fluxa.showcase.sections.inputsSection
import dev.fluxa.showcase.sections.layoutSection
import dev.fluxa.showcase.sections.listsSection
import dev.fluxa.showcase.sections.stateSection
import dev.fluxa.showcase.sections.stylesSection
import dev.fluxa.showcase.sections.themeSection
import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.style
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.HeroPanel
import dev.fluxa.ui.PillRow
import dev.fluxa.ui.SectionHeader
import dev.fluxa.ui.column
import dev.fluxa.ui.onClick
import dev.fluxa.ui.onCheckedChange
import dev.fluxa.ui.row
import dev.fluxa.ui.screen
import dev.fluxa.ui.stack
import dev.fluxa.ui.text
import dev.fluxa.ui.toggle

fun showcaseTree(
    theme: FluxaThemeTokens,
    isDark: Boolean,
    activeSection: String,
    onToggleDark: (Boolean) -> Unit,
    onSectionChange: (String) -> Unit,
): FluxaNode = screen(
    // --- Header ---
    HeroPanel(
        title = "Fluxa Showcase",
        subtitle = "Visual catalog of every component, style, and pattern.",
        theme = theme,
    ),

    // --- Theme toggle ---
    row(
        style = style {
            width("full")
            padding(FluxaAxisScale.MD)
            gap(FluxaAxisScale.MD)
            alignItems(FluxaAlignment.CENTER)
            justifyContent(FluxaAlignment.SPACE_BETWEEN)
        },
        text(
            value = if (isDark) "Aurora Dark" else "Aurora Light",
            style = style {
                foreground(theme.colors.textPrimary)
                typography(theme.typography.label)
            },
        ),
        toggle(label = "Dark mode", checked = isDark, style = style {
            padding(FluxaAxisScale.XS)
        }).onCheckedChange { onToggleDark(it) },
    ),

    // --- Section filter pills ---
    sectionPills(theme, activeSection, onSectionChange),

    // --- Sections ---
    *buildSections(theme, activeSection),
)

private val sections = listOf(
    "all" to "All",
    "cards" to "Cards",
    "inputs" to "Inputs",
    "forms" to "Forms",
    "lists" to "Lists",
    "layout" to "Layout",
    "state" to "State",
    "styles" to "Styles",
    "theme" to "Theme",
)

private fun sectionPills(
    theme: FluxaThemeTokens,
    active: String,
    onSelect: (String) -> Unit,
): FluxaNode = row(
    style = style {
        width("full")
        padding(FluxaAxisScale.SM)
        gap(FluxaAxisScale.SM)
    },
    *sections.map { (key, label) ->
        val isActive = key == active
        stack(
            style = style {
                if (isActive) {
                    background(theme.colors.spotlight)
                    foreground(theme.colors.spotlightText)
                } else {
                    background(theme.colors.pill)
                    foreground(theme.colors.pillText)
                }
                paddingX(FluxaAxisScale.MD)
                paddingY(FluxaAxisScale.SM)
                radius(FluxaRadiusScale.PILL)
            },
            text(label),
        ).onClick { onSelect(key) }
    }.toTypedArray(),
)

private fun buildSections(
    theme: FluxaThemeTokens,
    active: String,
): Array<FluxaNode> = buildList {
    if (active == "all" || active == "cards") {
        add(SectionHeader(title = "Cards & Components", theme = theme))
        addAll(cardsSection(theme))
    }
    if (active == "all" || active == "inputs") {
        add(SectionHeader(title = "Input Primitives", theme = theme))
        addAll(inputsSection(theme))
    }
    if (active == "all" || active == "forms") {
        add(SectionHeader(title = "Form Patterns", theme = theme))
        addAll(formsSection(theme))
    }
    if (active == "all" || active == "lists") {
        add(SectionHeader(title = "Lists & Grids", theme = theme))
        addAll(listsSection(theme))
    }
    if (active == "all" || active == "layout") {
        add(SectionHeader(title = "Layout Primitives", theme = theme))
        addAll(layoutSection(theme))
    }
    if (active == "all" || active == "state") {
        add(SectionHeader(title = "State Components", theme = theme))
        addAll(stateSection(theme))
    }
    if (active == "all" || active == "styles") {
        add(SectionHeader(title = "Style Showcase", theme = theme))
        addAll(stylesSection(theme))
    }
    if (active == "all" || active == "theme") {
        add(SectionHeader(title = "Theme Tokens", theme = theme))
        addAll(themeSection(theme))
    }
}.toTypedArray()
