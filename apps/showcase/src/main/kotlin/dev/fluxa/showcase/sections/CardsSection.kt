package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.ui.FeatureCard
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.NoticeCard
import dev.fluxa.ui.SectionCard
import dev.fluxa.ui.SelectableNotice
import dev.fluxa.ui.SelfAlignedPill
import dev.fluxa.ui.SpotlightCard
import dev.fluxa.ui.StatusBadge
import dev.fluxa.ui.text
import dev.fluxa.ui.withSemantics

fun cardsSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    FeatureCard(
        title = "FeatureCard",
        body = "Standard content card with title and body. Supports PRESSED variant with animated background transition.",
        theme = theme,
    ),

    SectionCard(
        title = "SectionCard",
        theme = theme,
        headerTrailing = StatusBadge(label = "Live", theme = theme),
        children = arrayOf(
            text("Card with a header row and trailing StatusBadge. Use for structured content with metadata."),
        ),
    ),

    SpotlightCard(
        title = "SpotlightCard",
        theme = theme,
        children = arrayOf(
            SelfAlignedPill(label = "Self-aligned", theme = theme),
        ),
    ),

    SelectableNotice(
        title = "SelectableNotice (selected)",
        body = "This notice uses SELECTED variant — background animates to warning color via transition DSL.",
        theme = theme,
        selected = true,
    ),

    SelectableNotice(
        title = "SelectableNotice (unselected)",
        body = "Same component without the SELECTED variant active. Compare backgrounds above and below.",
        theme = theme,
        selected = false,
    ),

    NoticeCard(
        title = "NoticeCard",
        body = "Static warning-styled card. No variant, no interaction — purely declarative.",
        theme = theme,
    ),

    FeatureCard(
        title = "Semantic Card",
        body = "This card has contentDescription and heading semantics attached for accessibility.",
        theme = theme,
    ).withSemantics(
        contentDescription = "Demo card showing accessibility support",
        heading = true,
    ),
)
