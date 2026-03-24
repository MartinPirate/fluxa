package dev.fluxa.ui

import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.styleOf
import dev.fluxa.test.assertNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FluxaNodeTest {

    @Test
    fun `screen creates Screen node`() {
        val node = screen()
        assertNode(node).hasType("Screen").hasNoChildren()
    }

    @Test
    fun `column creates Column node with children`() {
        val node = column(children = arrayOf(text("a"), text("b")))
        assertNode(node).hasType("Column").hasChildCount(2)
    }

    @Test
    fun `row creates Row node`() {
        val node = row()
        assertNode(node).hasType("Row")
    }

    @Test
    fun `stack creates Stack node`() {
        val node = stack()
        assertNode(node).hasType("Stack")
    }

    @Test
    fun `text creates Text node with value`() {
        val node = text("hello")
        assertNode(node).hasType("Text").hasText("hello")
    }

    @Test
    fun `withVariants adds variants`() {
        val node = text("x").withVariants(FluxaVariant.SELECTED)
        assertNode(node).hasVariant(FluxaVariant.SELECTED)
    }

    @Test
    fun `withVariants preserves existing variants`() {
        val node = text("x")
            .withVariants(FluxaVariant.PRESSED)
            .withVariants(FluxaVariant.SELECTED)
        assertNode(node)
            .hasVariant(FluxaVariant.PRESSED)
            .hasVariant(FluxaVariant.SELECTED)
    }

    @Test
    fun `withSemantics adds semantics`() {
        val node = text("img").withSemantics(
            contentDescription = "A photo",
            role = FluxaRole.IMAGE,
            heading = true,
        )
        assertNode(node).hasSemantics().hasContentDescription("A photo")
        assertEquals(FluxaRole.IMAGE, node.semantics?.role)
        assertTrue(node.semantics!!.heading)
    }

    @Test
    fun `default node has empty meta`() {
        val node = text("x")
        assertTrue(node.meta.isEmpty())
    }

    @Test
    fun `default node has null semantics`() {
        val node = text("x")
        assertNull(node.semantics)
    }

    @Test
    fun `debugTree produces readable output`() {
        val tree = screen(
            column(children = arrayOf(text("hello"))),
        )
        val output = tree.debugTree()
        assertTrue(output.contains("Screen"))
        assertTrue(output.contains("Column"))
        assertTrue(output.contains("hello"))
    }
}
