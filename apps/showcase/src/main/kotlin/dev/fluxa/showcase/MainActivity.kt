package dev.fluxa.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.fluxa.compose.FluxaRenderContext
import dev.fluxa.compose.FluxaTheme
import dev.fluxa.compose.RenderFluxaNode
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.FluxaThemes
import dev.fluxa.ui.FeatureCard
import dev.fluxa.ui.HeroPanel
import dev.fluxa.ui.NoticeCard
import dev.fluxa.ui.PillRow
import dev.fluxa.ui.SectionCard
import dev.fluxa.ui.SectionHeader
import dev.fluxa.ui.SelectableNotice
import dev.fluxa.ui.SelfAlignedPill
import dev.fluxa.ui.SpotlightCard
import dev.fluxa.ui.StatusBadge
import dev.fluxa.ui.button
import dev.fluxa.ui.checkbox
import dev.fluxa.ui.divider
import dev.fluxa.ui.screen
import dev.fluxa.ui.spacer
import dev.fluxa.ui.text
import dev.fluxa.ui.textField
import dev.fluxa.ui.toggle
import dev.fluxa.ui.withSemantics
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.style

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowcaseRoot()
        }
    }
}

@Composable
private fun ShowcaseRoot() {
    var useDark by remember { mutableStateOf(false) }
    val theme = if (useDark) FluxaThemes.AuroraDark else FluxaThemes.Aurora

    FluxaTheme(theme = theme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            RenderFluxaNode(
                node = showcaseTree(theme),
                context = FluxaRenderContext(activeVariants = setOf(FluxaVariant.EMPHASIS)),
            )
        }
    }
}

private fun showcaseTree(theme: FluxaThemeTokens) = screen(
    HeroPanel(
        title = "Fluxa",
        subtitle = "Android UI that feels stricter, faster, and more visual.",
        theme = theme,
    ),
    PillRow(
        labels = listOf("Responsive", "Variants", "Themes", "A11y"),
        theme = theme,
    ),

    // --- Components section ---
    SectionHeader(title = "Components", theme = theme),
    FeatureCard(
        title = "Composable bridge",
        body = "This card is rendered from FluxaNode through a Compose adapter.",
        theme = theme,
    ),
    SectionCard(
        title = "Typed styles",
        theme = theme,
        headerTrailing = StatusBadge(label = "Live", theme = theme),
        children = arrayOf(
            text("Utilities, tokens, variants, and breakpoints compile into a runtime style spec."),
        ),
    ),
    SpotlightCard(
        title = "Stack primitive",
        theme = theme,
        children = arrayOf(
            SelfAlignedPill(label = "Self aligned child", theme = theme),
        ),
    ),

    // --- State section ---
    SectionHeader(title = "Stateful components", theme = theme),
    SelectableNotice(
        title = "Node scoped variants",
        body = "This block opts into SELECTED on the node itself, not the whole render tree.",
        theme = theme,
        selected = true,
    ),
    NoticeCard(
        title = "Static notice",
        body = "A simple notice card without selectable state — purely declarative.",
        theme = theme,
    ),

    // --- Input section ---
    SectionHeader(title = "Input primitives", theme = theme),
    textField(
        label = "Email",
        placeholder = "you@example.com",
        style = FluxaStyles.textInput(theme),
    ),
    textField(
        label = "Password",
        placeholder = "Enter password",
        style = FluxaStyles.textInput(theme),
    ),
    toggle(label = "Dark mode", style = style {
        padding(FluxaAxisScale.SM)
    }),
    checkbox(label = "Accept terms", style = style {
        padding(FluxaAxisScale.SM)
    }),
    button(
        label = "Submit",
        style = FluxaStyles.primaryButton(theme),
    ),

    // --- Divider & spacing ---
    divider(style = FluxaStyles.divider(theme)),
    spacer(style = style { height("16") }),

    // --- Accessibility ---
    SectionHeader(title = "Accessibility", theme = theme),
    FeatureCard(
        title = "Semantic nodes",
        body = "FluxaNode supports contentDescription, role, heading, and liveRegion semantics.",
        theme = theme,
    ).withSemantics(
        contentDescription = "Accessibility demo card",
        heading = true,
    ),
)
