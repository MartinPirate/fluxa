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
import dev.fluxa.style.FluxaRadiusScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.style
import dev.fluxa.ui.column
import dev.fluxa.ui.row
import dev.fluxa.ui.screen
import dev.fluxa.ui.stack
import dev.fluxa.ui.text
import dev.fluxa.ui.withVariants

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
    column(
        style = FluxaStyles.heroPanel(appTheme),
        text("Fluxa"),
        text("Android UI that feels stricter, faster, and more visual."),
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
    column(
        style = FluxaStyles.adaptiveFeatureCard(appTheme),
        row(
            style = style {
                width("full")
                justifyContent(FluxaAlignment.SPACE_BETWEEN)
                alignItems(FluxaAlignment.CENTER)
            },
            text("Typed styles"),
            text(
                "Live",
                style = FluxaStyles.statusBadge(appTheme),
            ),
        ),
        text("Utilities, tokens, variants, and breakpoints already compile into a runtime style spec."),
    ),
    stack(
        style = FluxaStyles.spotlightCard(appTheme),
        text("Stack primitive"),
        text(
            "Self aligned child",
            style = style {
                background(appTheme.colors.pill)
                foreground(appTheme.colors.textPrimary)
                paddingX(FluxaAxisScale.MD)
                paddingY(FluxaAxisScale.XS)
                radius(FluxaRadiusScale.PILL)
                alignSelf(FluxaAlignment.END)
            },
        ),
    ),
    column(
        style = FluxaStyles.selectedNotice(appTheme),
        text("Node scoped variants"),
        text("This block opts into SELECTED on the node itself, not the whole render tree."),
    ).withVariants(FluxaVariant.SELECTED),
)
