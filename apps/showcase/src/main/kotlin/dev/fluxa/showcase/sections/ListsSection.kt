package dev.fluxa.showcase.sections

import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.ui.CardGrid
import dev.fluxa.ui.FeatureCard
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.ListItem
import dev.fluxa.ui.ScrollableList
import dev.fluxa.ui.StatusBadge
import dev.fluxa.ui.text

fun listsSection(theme: FluxaThemeTokens): List<FluxaNode> = listOf(
    // ListItem variants
    ListItem(
        title = "Simple ListItem",
        subtitle = "Just title and subtitle",
        theme = theme,
    ),

    ListItem(
        title = "ListItem with trailing",
        subtitle = "Has a StatusBadge on the right",
        theme = theme,
        trailing = StatusBadge(label = "New", theme = theme),
    ),

    ListItem(
        title = "ListItem with leading",
        subtitle = "Avatar placeholder on the left",
        theme = theme,
        leading = text("👤"),
    ),

    ListItem(
        title = "Full ListItem",
        subtitle = "Leading, trailing, title, and subtitle",
        theme = theme,
        leading = text("📄"),
        trailing = StatusBadge(label = "Draft", theme = theme),
    ),

    // CardGrid — horizontal scroll
    CardGrid(
        theme = theme,
        FeatureCard(title = "Card A", body = "Horizontal scroll", theme = theme),
        FeatureCard(title = "Card B", body = "Card grid layout", theme = theme),
        FeatureCard(title = "Card C", body = "Swipe to see more", theme = theme),
    ),

    // ScrollableList — vertical scroll
    ScrollableList(
        theme = theme,
        children = arrayOf(
            ListItem(title = "Item 1", subtitle = "In a ScrollableList", theme = theme),
            ListItem(title = "Item 2", subtitle = "Vertical lazy list", theme = theme),
            ListItem(title = "Item 3", subtitle = "Renders with LazyColumn", theme = theme),
            ListItem(title = "Item 4", subtitle = "Efficient for long lists", theme = theme),
        ),
    ),
)
