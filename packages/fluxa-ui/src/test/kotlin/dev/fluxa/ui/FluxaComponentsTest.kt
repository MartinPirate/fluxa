package dev.fluxa.ui

import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.FluxaVariant
import dev.fluxa.test.assertNode
import kotlin.test.Test
import kotlin.test.assertEquals

class FluxaComponentsTest {

    private val theme = FluxaThemes.Aurora

    @Test
    fun `HeroPanel creates column with title and subtitle`() {
        val node = HeroPanel("Title", "Sub", theme)
        assertNode(node).hasType("Column").hasChildCount(2)
        assertNode(node).firstChild().hasText("Title")
        assertNode(node).child(1).hasText("Sub")
    }

    @Test
    fun `StatusBadge creates text node`() {
        val node = StatusBadge("Live", theme)
        assertNode(node).hasType("Text").hasText("Live")
    }

    @Test
    fun `SpotlightCard creates stack with title and children`() {
        val node = SpotlightCard("Spot", theme, children = arrayOf(text("child")))
        assertNode(node).hasType("Stack").hasChildCount(2)
        assertNode(node).firstChild().hasText("Spot")
    }

    @Test
    fun `SectionCard creates column with header row`() {
        val node = SectionCard("Section", theme, children = arrayOf(text("body")))
        assertNode(node).hasType("Column").hasChildren()
        assertNode(node).firstChild().hasType("Row")
    }

    @Test
    fun `SelectableNotice applies SELECTED variant when selected`() {
        val node = SelectableNotice("Title", "Body", theme, selected = true)
        assertNode(node).hasVariant(FluxaVariant.SELECTED)
    }

    @Test
    fun `SelectableNotice has no variant when not selected`() {
        val node = SelectableNotice("Title", "Body", theme, selected = false)
        assertNode(node).hasNoVariant(FluxaVariant.SELECTED)
    }

    @Test
    fun `FeatureCard creates column with title and body`() {
        val node = FeatureCard("Feat", "Desc", theme)
        assertNode(node).hasType("Column").hasChildCount(2)
    }

    @Test
    fun `PillRow creates row from labels`() {
        val node = PillRow(listOf("A", "B", "C"), theme)
        assertNode(node).hasType("Row").hasChildCount(3)
    }

    @Test
    fun `NoticeCard creates column`() {
        val node = NoticeCard("Notice", "Body", theme)
        assertNode(node).hasType("Column").hasChildCount(2)
    }

    @Test
    fun `SectionHeader creates text node`() {
        val node = SectionHeader("Header", theme)
        assertNode(node).hasType("Text").hasText("Header")
    }
}
