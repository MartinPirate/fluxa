package dev.fluxa.style

import dev.fluxa.runtime.FluxaBreakpoint
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FluxaStyleTest {

    @Test
    fun `style builder creates padding instruction`() {
        val s = style { padding(FluxaAxisScale.LG) }
        val spec = s.compile()
        assertTrue(spec.base.any { it.name == "padding" && it.value == "6" })
    }

    @Test
    fun `style builder creates background with theme color`() {
        val theme = FluxaThemes.Aurora
        val s = style { background(theme.colors.page) }
        val spec = s.compile()
        assertTrue(spec.base.any { it.name == "background" && it.value == "color.page" })
    }

    @Test
    fun `style builder creates variant`() {
        val s = style {
            variant(FluxaVariant.PRESSED) {
                opacity(0.5f)
            }
        }
        val spec = s.compile()
        assertTrue(spec.variants.containsKey("pressed"))
        assertTrue(spec.variants["pressed"]!!.any { it.name == "opacity" })
    }

    @Test
    fun `style builder creates responsive rule`() {
        val s = style {
            responsive(FluxaBreakpoint.EXPANDED) {
                padding(FluxaAxisScale.XL)
            }
        }
        val spec = s.compile()
        assertTrue(spec.responsive.containsKey(FluxaBreakpoint.EXPANDED))
    }

    @Test
    fun `styleOf creates simple style`() {
        val s = styleOf(Padding(FluxaAxisScale.MD), Gap(FluxaAxisScale.SM))
        assertEquals(2, s.base.size)
    }

    @Test
    fun `withTheme injects tokens`() {
        val s = style { padding(FluxaAxisScale.SM) }
        val themed = s.withTheme(FluxaThemes.Aurora)
        assertTrue(themed.tokens.isNotEmpty())
        assertTrue(themed.tokens.any { it.name == "color.page" })
    }

    @Test
    fun `compile produces correct instruction count`() {
        val s = style {
            padding(FluxaAxisScale.LG)
            gap(FluxaAxisScale.MD)
            background("test")
            radius(FluxaRadiusScale.LG)
        }
        assertEquals(4, s.compile().base.size)
    }

    @Test
    fun `all axis scales have correct steps`() {
        assertEquals(0, FluxaAxisScale.NONE.step)
        assertEquals(1, FluxaAxisScale.XS.step)
        assertEquals(2, FluxaAxisScale.SM.step)
        assertEquals(4, FluxaAxisScale.MD.step)
        assertEquals(6, FluxaAxisScale.LG.step)
        assertEquals(8, FluxaAxisScale.XL.step)
        assertEquals(12, FluxaAxisScale.XXL.step)
    }

    @Test
    fun `all radius scales have correct steps`() {
        assertEquals(0, FluxaRadiusScale.NONE.step)
        assertEquals(999, FluxaRadiusScale.PILL.step)
    }

    @Test
    fun `width and height produce instructions`() {
        val s = style {
            width("full")
            height("40")
        }
        val spec = s.compile()
        assertTrue(spec.base.any { it.name == "width" && it.value == "full" })
        assertTrue(spec.base.any { it.name == "height" && it.value == "40" })
    }

    @Test
    fun `transition produces correct instruction`() {
        val s = style {
            transition("background", FluxaDuration.SLOW, FluxaEasing.EASE_OUT)
        }
        val spec = s.compile()
        val instruction = spec.base.single { it.name == "transition" }
        assertEquals("background|350|ease_out", instruction.value)
    }

    @Test
    fun `animateOn produces correct instruction`() {
        val s = style {
            animateOn(FluxaVariant.PRESSED, FluxaDuration.FAST, FluxaEasing.SPRING)
        }
        val spec = s.compile()
        val instruction = spec.base.single { it.name == "animateOn" }
        assertEquals("pressed|100|spring", instruction.value)
    }

    @Test
    fun `transition default values`() {
        val s = style {
            transition("opacity")
        }
        val spec = s.compile()
        val instruction = spec.base.single { it.name == "transition" }
        assertEquals("opacity|200|ease_in_out", instruction.value)
    }

    @Test
    fun `multiple transitions coexist`() {
        val s = style {
            transition("background", FluxaDuration.NORMAL)
            transition("opacity", FluxaDuration.FAST)
            transition("foreground", FluxaDuration.SLOW)
        }
        val spec = s.compile()
        val transitions = spec.base.filter { it.name == "transition" }
        assertEquals(3, transitions.size)
        assertTrue(transitions.any { it.value.startsWith("background|") })
        assertTrue(transitions.any { it.value.startsWith("opacity|") })
        assertTrue(transitions.any { it.value.startsWith("foreground|") })
    }

    @Test
    fun `all FluxaDuration values have correct milliseconds`() {
        assertEquals(0, FluxaDuration.INSTANT.ms)
        assertEquals(100, FluxaDuration.FAST.ms)
        assertEquals(200, FluxaDuration.NORMAL.ms)
        assertEquals(350, FluxaDuration.SLOW.ms)
        assertEquals(250, FluxaDuration.ENTER.ms)
        assertEquals(200, FluxaDuration.EXIT.ms)
    }

    @Test
    fun `all FluxaEasing values exist`() {
        assertEquals(5, FluxaEasing.entries.size)
        assertTrue(FluxaEasing.entries.map { it.name }.containsAll(
            listOf("LINEAR", "EASE_IN", "EASE_OUT", "EASE_IN_OUT", "SPRING")
        ))
    }

    @Test
    fun `transition combined with variant style`() {
        val s = style {
            background("color.panel")
            opacity(1.0f)
            variant(FluxaVariant.PRESSED) {
                background("color.accent")
                opacity(0.8f)
            }
            transition("background", FluxaDuration.NORMAL)
            transition("opacity", FluxaDuration.FAST)
        }
        val spec = s.compile()
        assertTrue(spec.base.any { it.name == "background" })
        assertTrue(spec.base.any { it.name == "opacity" })
        assertTrue(spec.variants["pressed"]!!.any { it.name == "background" })
        assertTrue(spec.variants["pressed"]!!.any { it.name == "opacity" })
        assertEquals(2, spec.base.count { it.name == "transition" })
    }
}
