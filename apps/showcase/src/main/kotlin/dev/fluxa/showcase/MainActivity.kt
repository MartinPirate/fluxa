package dev.fluxa.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.fluxa.compose.FluxaRenderContext
import dev.fluxa.compose.FluxaTheme
import dev.fluxa.compose.RenderFluxaNode
import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.style.FluxaAlignment
import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.style
import dev.fluxa.ui.HeroPanel
import dev.fluxa.ui.SectionCard
import dev.fluxa.ui.SelectableNotice
import dev.fluxa.ui.SelfAlignedPill
import dev.fluxa.ui.SpotlightCard
import dev.fluxa.ui.StatusBadge
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.screen
import dev.fluxa.ui.stack
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
    row(
        style = style {
            width("full")
            gap(FluxaAxisScale.SM)
            justifyContent(FluxaAlignment.SPACE_BETWEEN)
            responsive(FluxaBreakpoint.MEDIUM) {
                gap(FluxaAxisScale.MD)
            }
        },
        stack(
            style = FluxaStyles.pill(appTheme),
            text("Responsive"),
        ),
        stack(
            style = FluxaStyles.pill(appTheme),
            text("Variants"),
        ),
    ),
    column(
        style = FluxaStyles.surfaceCard(appTheme),
        text("Composable bridge"),
        text("This card is rendered from FluxaNode through a Compose adapter."),
    ),
    SectionCard(
        "Typed styles",
        appTheme,
        FluxaStyles.adaptiveFeatureCard(appTheme),
        StatusBadge(
            label = "Live",
            theme = appTheme,
        ),
        text("Utilities, tokens, variants, and breakpoints already compile into a runtime style spec."),
    ),
    SpotlightCard(
        "Stack primitive",
        appTheme,
        FluxaStyles.spotlightCard(appTheme),
        SelfAlignedPill(
            label = "Self aligned child",
            theme = appTheme,
        ),
    ),
    SelectableNotice(
        title = "Node scoped variants",
        body = "This block opts into SELECTED on the node itself, not the whole render tree.",
        theme = appTheme,
        selected = true,
    ),
)
