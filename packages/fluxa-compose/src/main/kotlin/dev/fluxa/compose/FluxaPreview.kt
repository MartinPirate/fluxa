package dev.fluxa.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.ui.FluxaNode

@Composable
fun FluxaPreviewFrame(
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    context: FluxaRenderContext = FluxaRenderContext(),
    node: () -> FluxaNode,
) {
    FluxaTheme(theme = theme) {
        RenderFluxaNode(
            node = node(),
            context = context,
        )
    }
}

@Composable
fun FluxaDarkPreviewFrame(
    context: FluxaRenderContext = FluxaRenderContext(),
    node: () -> FluxaNode,
) {
    FluxaPreviewFrame(
        theme = FluxaThemes.AuroraDark,
        context = context,
        node = node,
    )
}
