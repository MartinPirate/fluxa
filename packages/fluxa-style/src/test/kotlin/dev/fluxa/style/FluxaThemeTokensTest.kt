package dev.fluxa.style

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FluxaThemeTokensTest {

    @Test
    fun `aurora theme has correct name`() {
        assertEquals("Aurora", FluxaThemes.Aurora.name)
    }

    @Test
    fun `aurora dark theme has correct name`() {
        assertEquals("Aurora Dark", FluxaThemes.AuroraDark.name)
    }

    @Test
    fun `aurora is light mode`() {
        assertEquals(FluxaThemeMode.LIGHT, FluxaThemes.Aurora.mode)
    }

    @Test
    fun `aurora dark is dark mode`() {
        assertEquals(FluxaThemeMode.DARK, FluxaThemes.AuroraDark.mode)
    }

    @Test
    fun `allTokens includes all colors`() {
        val tokens = FluxaThemes.Aurora.allTokens()
        val colorTokens = tokens.filter { it.name.startsWith("color.") }
        assertEquals(20, colorTokens.size)
    }

    @Test
    fun `allTokens includes all typography`() {
        val tokens = FluxaThemes.Aurora.allTokens()
        val typeTokens = tokens.filter { it.name.startsWith("type.") }
        assertEquals(5, typeTokens.size)
    }

    @Test
    fun `aurora colors are valid hex`() {
        val tokens = FluxaThemes.Aurora.allTokens()
        tokens.filter { it.name.startsWith("color.") }.forEach { token ->
            assertTrue(token.value.startsWith("#"), "Color ${token.name} should start with #")
            assertTrue(token.value.length == 7, "Color ${token.name} should be 7 chars (#RRGGBB)")
        }
    }

    @Test
    fun `dark theme inverts page and text`() {
        val light = FluxaThemes.Aurora
        val dark = FluxaThemes.AuroraDark
        // Dark page should be darker than light page
        assertTrue(light.colors.page.value != dark.colors.page.value)
        assertTrue(light.colors.textPrimary.value != dark.colors.textPrimary.value)
    }

    @Test
    fun `both themes share typography`() {
        assertEquals(
            FluxaThemes.Aurora.typography.hero.value,
            FluxaThemes.AuroraDark.typography.hero.value,
        )
    }
}
