package dev.fluxa.runtime

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FluxaStyleSpecTest {

    @Test
    fun `empty spec has no instructions`() {
        val spec = FluxaStyleSpec()
        assertTrue(spec.base.isEmpty())
        assertTrue(spec.tokens.isEmpty())
        assertTrue(spec.variants.isEmpty())
        assertTrue(spec.responsive.isEmpty())
    }

    @Test
    fun `spec stores base instructions`() {
        val spec = FluxaStyleSpec(
            base = listOf(
                FluxaStyleInstruction("padding", "8"),
                FluxaStyleInstruction("background", "color.page"),
            ),
        )
        assertEquals(2, spec.base.size)
        assertEquals("padding", spec.base[0].name)
        assertEquals("8", spec.base[0].value)
    }

    @Test
    fun `spec stores tokens`() {
        val spec = FluxaStyleSpec(
            tokens = mapOf("color.page" to "#FFFFFF"),
        )
        assertEquals("#FFFFFF", spec.tokens["color.page"])
    }

    @Test
    fun `spec stores variant instructions`() {
        val spec = FluxaStyleSpec(
            variants = mapOf(
                "pressed" to listOf(FluxaStyleInstruction("opacity", "0.8")),
            ),
        )
        assertEquals(1, spec.variants["pressed"]?.size)
    }

    @Test
    fun `spec stores responsive rules by breakpoint`() {
        val spec = FluxaStyleSpec(
            responsive = mapOf(
                FluxaBreakpoint.EXPANDED to listOf(FluxaStyleInstruction("padding", "12")),
            ),
        )
        assertTrue(spec.responsive.containsKey(FluxaBreakpoint.EXPANDED))
    }

    @Test
    fun `breakpoint enum has three values`() {
        assertEquals(3, FluxaBreakpoint.entries.size)
        assertEquals(FluxaBreakpoint.COMPACT, FluxaBreakpoint.entries[0])
        assertEquals(FluxaBreakpoint.MEDIUM, FluxaBreakpoint.entries[1])
        assertEquals(FluxaBreakpoint.EXPANDED, FluxaBreakpoint.entries[2])
    }

    @Test
    fun `instruction equality`() {
        val a = FluxaStyleInstruction("padding", "8")
        val b = FluxaStyleInstruction("padding", "8")
        assertEquals(a, b)
    }
}
