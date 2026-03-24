package dev.fluxa.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.fluxa.compose.FluxaRenderContext
import dev.fluxa.compose.FluxaTheme
import dev.fluxa.compose.RenderFluxaNode
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
import dev.fluxa.ui.screen
import dev.fluxa.ui.text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FluxaTheme(theme = appTheme) {
                RenderFluxaNode(
                    node = appTree(),
                    context = FluxaRenderContext(activeVariants = setOf(FluxaVariant.EMPHASIS)),
                )
            }
        }
    }
}

private val appTheme = FluxaThemes.Aurora

private fun appTree() = screen(
    HeroPanel(
        title = "Fluxa",
        subtitle = "Android UI that feels stricter, faster, and more visual.",
        theme = appTheme,
    ),
    PillRow(
        labels = listOf("Responsive", "Variants"),
        theme = appTheme,
    ),
    FeatureCard(
        title = "Composable bridge",
        body = "This card is rendered from FluxaNode through a Compose adapter.",
        theme = appTheme,
    ),
    SectionHeader(
        title = "Core primitives",
        theme = appTheme,
    ),
    SectionCard(
        title = "Typed styles",
        theme = appTheme,
        headerTrailing = StatusBadge(
            label = "Live",
            theme = appTheme,
        ),
        children = arrayOf(
            text("Utilities, tokens, variants, and breakpoints already compile into a runtime style spec."),
        ),
    ),
    SpotlightCard(
        title = "Stack primitive",
        theme = appTheme,
        children = arrayOf(
            SelfAlignedPill(
                label = "Self aligned child",
                theme = appTheme,
            ),
        ),
    ),
    SectionHeader(
        title = "Stateful components",
        theme = appTheme,
    ),
    SelectableNotice(
        title = "Node scoped variants",
        body = "This block opts into SELECTED on the node itself, not the whole render tree.",
        theme = appTheme,
        selected = true,
    ),
    NoticeCard(
        title = "Static notice",
        body = "A simple notice card without selectable state — purely declarative.",
        theme = appTheme,
    ),
)
