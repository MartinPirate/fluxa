package dev.fluxa.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.runtime.FluxaStyleInstruction
import dev.fluxa.runtime.FluxaStyleSpec
import dev.fluxa.style.FluxaVariant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class FluxaStyleResolverTest {

    private fun spec(vararg instructions: Pair<String, String>) = FluxaStyleSpec(
        base = instructions.map { FluxaStyleInstruction(it.first, it.second) },
    )

    private fun resolve(
        spec: FluxaStyleSpec,
        context: FluxaRenderContext = FluxaRenderContext(),
    ) = spec.resolve(context)

    // --- Background ---

    @Test
    fun `background resolves to backgroundColor`() {
        val result = resolve(spec("background" to "#FF0000"))
        assertNotNull(result.backgroundColor)
        assertEquals(Color(0xFFFF0000), result.backgroundColor)
    }

    @Test
    fun `no background leaves backgroundColor null`() {
        val result = resolve(spec("padding" to "8"))
        assertNull(result.backgroundColor)
    }

    // --- Foreground ---

    @Test
    fun `foreground resolves to color`() {
        val result = resolve(spec("foreground" to "#00FF00"))
        assertNotNull(result.foreground)
        assertEquals(Color(0xFF00FF00), result.foreground)
    }

    // --- Opacity ---

    @Test
    fun `opacity resolves to float`() {
        val result = resolve(spec("opacity" to "0.5"))
        assertEquals(0.5f, result.opacity, 0.001f)
    }

    @Test
    fun `default opacity is 1`() {
        val result = resolve(spec("padding" to "8"))
        assertEquals(1f, result.opacity, 0.001f)
    }

    // --- Radius ---

    @Test
    fun `radius resolves to radiusDp`() {
        val result = resolve(spec("radius" to "16"))
        assertEquals(16, result.radiusDp)
    }

    // --- Border ---

    @Test
    fun `border resolves color and width`() {
        val result = resolve(spec("border" to "#0000FF|2"))
        assertNotNull(result.borderColor)
        assertEquals(Color(0xFF0000FF), result.borderColor)
        assertEquals(2, result.borderWidthDp)
    }

    @Test
    fun `border defaults to 1dp width`() {
        val result = resolve(spec("border" to "#0000FF"))
        assertEquals(1, result.borderWidthDp)
    }

    // --- Shadow ---

    @Test
    fun `shadow resolves to shadowDp`() {
        val result = resolve(spec("shadow" to "4"))
        assertEquals(4, result.shadowDp)
    }

    // --- Gap ---

    @Test
    fun `gap sets both arrangements to spacedBy`() {
        val result = resolve(spec("gap" to "8"))
        assertEquals(Arrangement.spacedBy(8.dp()), result.horizontalArrangement)
        assertEquals(Arrangement.spacedBy(8.dp()), result.verticalArrangement)
    }

    // --- Alignment ---

    @Test
    fun `alignItems center sets all alignments`() {
        val result = resolve(spec("alignItems" to "center"))
        assertEquals(Alignment.CenterHorizontally, result.horizontalAlignment)
        assertEquals(Alignment.CenterVertically, result.verticalAlignment)
        assertEquals(Alignment.Center, result.contentAlignment)
    }

    @Test
    fun `alignItems end sets end alignments`() {
        val result = resolve(spec("alignItems" to "end"))
        assertEquals(Alignment.End, result.horizontalAlignment)
        assertEquals(Alignment.Bottom, result.verticalAlignment)
    }

    @Test
    fun `justifyContent space_between`() {
        val result = resolve(spec("justifyContent" to "space_between"))
        assertEquals(Arrangement.SpaceBetween, result.horizontalArrangement)
        assertEquals(Arrangement.SpaceBetween, result.verticalArrangement)
    }

    @Test
    fun `alignSelf center`() {
        val result = resolve(spec("alignSelf" to "center"))
        assertEquals("center", result.selfAlignment)
    }

    // --- Transitions ---

    @Test
    fun `transition instruction parsed correctly`() {
        val result = resolve(spec("transition" to "background|200|ease_in_out"))
        assertEquals(1, result.transitions.size)
        val t = result.transitions[0]
        assertEquals("background", t.property)
        assertEquals(200, t.durationMs)
        assertEquals("ease_in_out", t.easing)
    }

    @Test
    fun `animateOn parsed as transition`() {
        val result = resolve(spec("animateOn" to "pressed|100|spring"))
        assertEquals(1, result.transitions.size)
        assertEquals("pressed", result.transitions[0].property)
        assertEquals(100, result.transitions[0].durationMs)
        assertEquals("spring", result.transitions[0].easing)
    }

    @Test
    fun `multiple transitions collected`() {
        val result = resolve(spec(
            "transition" to "background|200|ease_in_out",
            "transition" to "opacity|100|ease_out",
        ))
        assertEquals(2, result.transitions.size)
    }

    @Test
    fun `transition defaults when parts missing`() {
        val result = resolve(spec("transition" to "background"))
        val t = result.transitions[0]
        assertEquals(200, t.durationMs)
        assertEquals("ease_in_out", t.easing)
    }

    @Test
    fun `transitionFor finds matching transition`() {
        val result = resolve(spec(
            "transition" to "background|200|ease_in_out",
            "transition" to "opacity|100|ease_out",
        ))
        val bg = result.transitionFor("background")
        assertNotNull(bg)
        assertEquals(200, bg!!.durationMs)
        assertNull(result.transitionFor("shadow"))
    }

    @Test
    fun `hasAnimations true when transitions present`() {
        val result = resolve(spec("transition" to "background|200|ease_in_out"))
        assertTrue(result.hasAnimations)
    }

    @Test
    fun `hasAnimations false when no transitions`() {
        val result = resolve(spec("padding" to "8"))
        assertTrue(!result.hasAnimations)
    }

    // --- Variants ---

    @Test
    fun `variant overrides base values`() {
        val variantSpec = FluxaStyleSpec(
            base = listOf(
                FluxaStyleInstruction("background", "#FF0000"),
                FluxaStyleInstruction("opacity", "1.0"),
            ),
            variants = mapOf(
                "pressed" to listOf(
                    FluxaStyleInstruction("background", "#00FF00"),
                    FluxaStyleInstruction("opacity", "0.8"),
                ),
            ),
        )
        // Without variant
        val base = variantSpec.resolve(FluxaRenderContext())
        assertEquals(Color(0xFFFF0000), base.backgroundColor)
        assertEquals(1.0f, base.opacity, 0.001f)

        // With variant
        val pressed = variantSpec.resolve(FluxaRenderContext(
            activeVariants = setOf(FluxaVariant.PRESSED),
        ))
        assertEquals(Color(0xFF00FF00), pressed.backgroundColor)
        assertEquals(0.8f, pressed.opacity, 0.001f)
    }

    // --- Responsive ---

    @Test
    fun `responsive rules apply for matching breakpoint`() {
        val responsiveSpec = FluxaStyleSpec(
            base = listOf(FluxaStyleInstruction("opacity", "1.0")),
            responsive = mapOf(
                FluxaBreakpoint.EXPANDED to listOf(
                    FluxaStyleInstruction("opacity", "0.9"),
                ),
            ),
        )
        val compact = responsiveSpec.resolve(FluxaRenderContext(breakpoint = FluxaBreakpoint.COMPACT))
        assertEquals(1.0f, compact.opacity, 0.001f)

        val expanded = responsiveSpec.resolve(FluxaRenderContext(breakpoint = FluxaBreakpoint.EXPANDED))
        assertEquals(0.9f, expanded.opacity, 0.001f)
    }

    // --- Token Resolution ---

    @Test
    fun `token-based colors resolve through token map`() {
        val tokenSpec = FluxaStyleSpec(
            base = listOf(FluxaStyleInstruction("background", "color.primary")),
            tokens = mapOf("color.primary" to "#123456"),
        )
        val result = tokenSpec.resolve(FluxaRenderContext())
        assertEquals(Color(0xFF123456), result.backgroundColor)
    }

    // --- Typography ---

    @Test
    fun `typography hero resolves correctly`() {
        val tokenSpec = FluxaStyleSpec(
            base = listOf(FluxaStyleInstruction("typography", "type.hero")),
            tokens = mapOf("type.hero" to "hero"),
        )
        val result = tokenSpec.resolve(FluxaRenderContext())
        assertNotNull(result.textStyle)
        assertEquals(FontWeight.Bold, result.textStyle!!.fontWeight)
    }

    @Test
    fun `fontWeight 600 resolves to SemiBold`() {
        val result = resolve(spec("fontWeight" to "600"))
        assertNotNull(result.textStyle)
        assertEquals(FontWeight.SemiBold, result.textStyle!!.fontWeight)
    }

    // --- Combined style with multiple instructions ---

    @Test
    fun `full card style resolves all properties`() {
        val cardSpec = FluxaStyleSpec(
            base = listOf(
                FluxaStyleInstruction("background", "#E8EEF9"),
                FluxaStyleInstruction("foreground", "#0F1720"),
                FluxaStyleInstruction("padding", "6"),
                FluxaStyleInstruction("gap", "4"),
                FluxaStyleInstruction("radius", "16"),
                FluxaStyleInstruction("border", "#C2D6F6|1"),
                FluxaStyleInstruction("shadow", "2"),
                FluxaStyleInstruction("opacity", "1.0"),
                FluxaStyleInstruction("transition", "background|200|ease_in_out"),
            ),
        )
        val result = cardSpec.resolve(FluxaRenderContext())
        assertEquals(Color(0xFFE8EEF9), result.backgroundColor)
        assertEquals(Color(0xFF0F1720), result.foreground)
        assertEquals(16, result.radiusDp)
        assertEquals(Color(0xFFC2D6F6), result.borderColor)
        assertEquals(1, result.borderWidthDp)
        assertEquals(2, result.shadowDp)
        assertEquals(1.0f, result.opacity, 0.001f)
        assertEquals(1, result.transitions.size)
    }

    // Helper to avoid importing dp everywhere
    private fun Int.dp() = androidx.compose.ui.unit.Dp(this.toFloat())
}
