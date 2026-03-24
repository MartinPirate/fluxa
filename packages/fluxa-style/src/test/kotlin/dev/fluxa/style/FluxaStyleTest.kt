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
}
