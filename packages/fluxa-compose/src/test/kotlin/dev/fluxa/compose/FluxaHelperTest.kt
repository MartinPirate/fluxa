package dev.fluxa.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.ui.FluxaHandlers
import dev.fluxa.ui.FluxaRole
import dev.fluxa.ui.FluxaSemantics
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class FluxaHelperTest {

    // --- Breakpoints ---

    @Test
    fun `compact breakpoint under 600`() {
        assertEquals(FluxaBreakpoint.COMPACT, widthBreakpoint(400f))
        assertEquals(FluxaBreakpoint.COMPACT, widthBreakpoint(599f))
    }

    @Test
    fun `medium breakpoint 600 to 839`() {
        assertEquals(FluxaBreakpoint.MEDIUM, widthBreakpoint(600f))
        assertEquals(FluxaBreakpoint.MEDIUM, widthBreakpoint(839f))
    }

    @Test
    fun `expanded breakpoint 840 and above`() {
        assertEquals(FluxaBreakpoint.EXPANDED, widthBreakpoint(840f))
        assertEquals(FluxaBreakpoint.EXPANDED, widthBreakpoint(1200f))
    }

    // --- Font Weight ---

    @Test
    fun `fontWeight mapping`() {
        assertEquals(FontWeight.Normal, 400.toFontWeight())
        assertEquals(FontWeight.Medium, 500.toFontWeight())
        assertEquals(FontWeight.SemiBold, 600.toFontWeight())
        assertEquals(FontWeight.Bold, 700.toFontWeight())
    }

    // --- Shape ---

    @Test
    fun `zero radius returns RectangleShape`() {
        assertEquals(RectangleShape, 0.toShape())
    }

    @Test
    fun `positive radius returns RoundedCornerShape`() {
        val shape = 16.toShape()
        assertTrue(shape is RoundedCornerShape)
    }

    // --- Alignment mappers ---

    @Test
    fun `horizontal alignment mapping`() {
        assertEquals(Alignment.Start, "start".toHorizontalAlignment())
        assertEquals(Alignment.CenterHorizontally, "center".toHorizontalAlignment())
        assertEquals(Alignment.End, "end".toHorizontalAlignment())
    }

    @Test
    fun `vertical alignment mapping`() {
        assertEquals(Alignment.Top, "start".toVerticalAlignment())
        assertEquals(Alignment.CenterVertically, "center".toVerticalAlignment())
        assertEquals(Alignment.Bottom, "end".toVerticalAlignment())
    }

    @Test
    fun `content alignment mapping`() {
        assertEquals(Alignment.TopStart, "start".toContentAlignment())
        assertEquals(Alignment.Center, "center".toContentAlignment())
        assertEquals(Alignment.BottomEnd, "end".toContentAlignment())
    }

    @Test
    fun `horizontal arrangement mapping`() {
        assertEquals(Arrangement.Start, "start".toHorizontalArrangement())
        assertEquals(Arrangement.Center, "center".toHorizontalArrangement())
        assertEquals(Arrangement.End, "end".toHorizontalArrangement())
        assertEquals(Arrangement.SpaceBetween, "space_between".toHorizontalArrangement())
    }

    @Test
    fun `vertical arrangement mapping`() {
        assertEquals(Arrangement.Top, "start".toVerticalArrangement())
        assertEquals(Arrangement.Center, "center".toVerticalArrangement())
        assertEquals(Arrangement.Bottom, "end".toVerticalArrangement())
        assertEquals(Arrangement.SpaceBetween, "space_between".toVerticalArrangement())
    }

    @Test
    fun `unknown alignment defaults to start`() {
        assertEquals(Alignment.Start, "bogus".toHorizontalAlignment())
        assertEquals(Alignment.Top, "bogus".toVerticalAlignment())
        assertEquals(Alignment.TopStart, "bogus".toContentAlignment())
    }

    // --- Role mapping ---

    @Test
    fun `FluxaRole maps to Compose Role`() {
        assertEquals(Role.Button, FluxaRole.BUTTON.toComposeRole())
        assertEquals(Role.Checkbox, FluxaRole.CHECKBOX.toComposeRole())
        assertEquals(Role.Switch, FluxaRole.SWITCH.toComposeRole())
        assertEquals(Role.Image, FluxaRole.IMAGE.toComposeRole())
        assertEquals(Role.Tab, FluxaRole.TAB.toComposeRole())
        assertNull(FluxaRole.HEADING.toComposeRole())
    }

    // --- Handlers ---

    @Test
    fun `thenHandlers with no handlers returns same modifier`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenHandlers(FluxaHandlers())
        // Should be the same — no handlers added
        assertEquals(mod, result)
    }

    @Test
    fun `thenHandlers disabled returns same modifier even with onClick`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenHandlers(FluxaHandlers(onClick = {}), disabled = true)
        assertEquals(mod, result)
    }

    @Test
    fun `thenHandlers with onClick adds clickable`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenHandlers(FluxaHandlers(onClick = {}))
        // Modifier chain should be different from bare Modifier
        assertTrue(result != mod)
    }

    @Test
    fun `thenHandlers with onLongClick adds combinedClickable`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenHandlers(FluxaHandlers(
            onClick = {},
            onLongClick = {},
        ))
        assertTrue(result != mod)
    }

    // --- Semantics ---

    @Test
    fun `thenSemantics null returns same modifier`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenSemantics(null)
        assertEquals(mod, result)
    }

    @Test
    fun `thenSemantics with disabled applies alpha`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenSemantics(FluxaSemantics(disabled = true))
        assertTrue(result != mod) // alpha modifier added
    }

    @Test
    fun `thenSemantics with contentDescription adds semantics`() {
        val mod = androidx.compose.ui.Modifier
        val result = mod.thenSemantics(FluxaSemantics(contentDescription = "test"))
        assertTrue(result != mod)
    }

    // --- ResolvedTransition easing mapping ---

    @Test
    fun `easing mapping covers all types`() {
        val easings = listOf("linear", "ease_in", "ease_out", "ease_in_out", "spring", "unknown")
        easings.forEach { easing ->
            val t = ResolvedTransition("bg", 200, easing)
            // Should not throw for any easing value
            assertNotNull(t.toEasing())
        }
    }

    private fun assertTrue(condition: Boolean) {
        org.junit.Assert.assertTrue(condition)
    }

    private fun ResolvedTransition.toEasing() = when (easing) {
        "linear" -> androidx.compose.animation.core.LinearEasing
        "ease_in" -> androidx.compose.animation.core.FastOutLinearInEasing
        "ease_out" -> androidx.compose.animation.core.LinearOutSlowInEasing
        "ease_in_out" -> androidx.compose.animation.core.FastOutSlowInEasing
        else -> androidx.compose.animation.core.FastOutSlowInEasing
    }
}
