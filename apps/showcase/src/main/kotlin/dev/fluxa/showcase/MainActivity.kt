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
import dev.fluxa.style.FluxaThemes
import dev.fluxa.ui.FluxaNode
import dev.fluxa.ui.onClick
import dev.fluxa.ui.onCheckedChange
import dev.fluxa.ui.onValueChange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShowcaseRoot() }
    }
}

@Composable
private fun ShowcaseRoot() {
    var useDark by remember { mutableStateOf(false) }
    var activeSection by remember { mutableStateOf("all") }
    val theme = if (useDark) FluxaThemes.AuroraDark else FluxaThemes.Aurora

    FluxaTheme(theme = theme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            RenderFluxaNode(
                node = showcaseTree(
                    theme = theme,
                    isDark = useDark,
                    activeSection = activeSection,
                    onToggleDark = { useDark = it },
                    onSectionChange = { activeSection = it },
                ),
                context = FluxaRenderContext(),
            )
        }
    }
}
