package dev.fluxa.test

import dev.fluxa.style.FluxaVariant
import dev.fluxa.ui.column
import dev.fluxa.ui.screen
import dev.fluxa.ui.text
import dev.fluxa.ui.withSemantics
import dev.fluxa.ui.withVariants
import dev.fluxa.ui.FluxaRole
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertFailsWith

class FluxaNodeAssertionsTest {

    @Test
    fun `hasType passes on correct type`() {
        assertNode(text("x")).hasType("Text")
    }

    @Test
    fun `hasType fails on wrong type`() {
        assertFailsWith<IllegalStateException> {
            assertNode(text("x")).hasType("Column")
        }
    }

    @Test
    fun `hasText passes on correct text`() {
        assertNode(text("hello")).hasText("hello")
    }

    @Test
    fun `hasChildCount passes on correct count`() {
        val node = column(children = arrayOf(text("a"), text("b")))
        assertNode(node).hasChildCount(2)
    }

    @Test
    fun `child navigates to child`() {
        val node = column(children = arrayOf(text("first"), text("second")))
        assertNode(node).child(0).hasText("first")
        assertNode(node).child(1).hasText("second")
    }

    @Test
    fun `findByType finds nested node`() {
        val tree = screen(column(children = arrayOf(text("deep"))))
        val found = assertNode(tree).findByType("Text")
        assertNotNull(found)
        found.hasText("deep")
    }

    @Test
    fun `findByText finds node by text`() {
        val tree = screen(text("target"))
        val found = assertNode(tree).findByText("target")
        assertNotNull(found)
    }

    @Test
    fun `allByType returns all matches`() {
        val tree = screen(text("a"), text("b"), column(children = arrayOf(text("c"))))
        val matches = assertNode(tree).allByType("Text")
        assertEquals(3, matches.size)
    }

    @Test
    fun `hasVariant passes when variant present`() {
        val node = text("x").withVariants(FluxaVariant.SELECTED)
        assertNode(node).hasVariant(FluxaVariant.SELECTED)
    }

    @Test
    fun `hasSemantics passes when semantics present`() {
        val node = text("x").withSemantics(contentDescription = "desc")
        assertNode(node).hasSemantics().hasContentDescription("desc")
    }
}
