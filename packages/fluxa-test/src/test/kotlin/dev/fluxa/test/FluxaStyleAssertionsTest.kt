package dev.fluxa.test

import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.style
import kotlin.test.Test
import kotlin.test.assertFailsWith

class FluxaStyleAssertionsTest {

    @Test
    fun `hasInstruction passes when present`() {
        val s = style { padding(FluxaAxisScale.LG) }
        assertStyle(s).hasInstruction("padding")
    }

    @Test
    fun `hasInstruction with value passes on match`() {
        val s = style { padding(FluxaAxisScale.LG) }
        assertStyle(s).hasInstruction("padding", "6")
    }

    @Test
    fun `hasInstruction fails when missing`() {
        val s = style { padding(FluxaAxisScale.LG) }
        assertFailsWith<IllegalStateException> {
            assertStyle(s).hasInstruction("background")
        }
    }

    @Test
    fun `hasNoInstruction passes when absent`() {
        val s = style { padding(FluxaAxisScale.LG) }
        assertStyle(s).hasNoInstruction("background")
    }

    @Test
    fun `hasVariant passes when present`() {
        val s = style {
            variant(FluxaVariant.PRESSED) { opacity(0.5f) }
        }
        assertStyle(s).hasVariant(FluxaVariant.PRESSED)
    }

    @Test
    fun `hasNoVariant passes when absent`() {
        val s = style { padding(FluxaAxisScale.LG) }
        assertStyle(s).hasNoVariant(FluxaVariant.PRESSED)
    }

    @Test
    fun `variantHasInstruction verifies variant content`() {
        val s = style {
            variant(FluxaVariant.PRESSED) { opacity(0.8f) }
        }
        assertStyle(s).variantHasInstruction(FluxaVariant.PRESSED, "opacity", "0.8")
    }
}
