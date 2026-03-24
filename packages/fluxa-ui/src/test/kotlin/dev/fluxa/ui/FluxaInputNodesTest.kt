package dev.fluxa.ui

import dev.fluxa.test.assertNode
import kotlin.test.Test

class FluxaInputNodesTest {

    @Test
    fun `textField creates TextField node`() {
        val node = textField(label = "Email", placeholder = "you@example.com")
        assertNode(node).hasType("TextField").hasText("Email")
        assertNode(node).hasMeta("placeholder", "you@example.com")
        assertNode(node).hasMeta("enabled", "true")
    }

    @Test
    fun `toggle creates Toggle node`() {
        val node = toggle(label = "Dark mode", checked = true)
        assertNode(node).hasType("Toggle").hasText("Dark mode")
        assertNode(node).hasMeta("checked", "true")
    }

    @Test
    fun `checkbox creates Checkbox node`() {
        val node = checkbox(label = "Accept", checked = false)
        assertNode(node).hasType("Checkbox").hasText("Accept")
        assertNode(node).hasMeta("checked", "false")
    }

    @Test
    fun `button creates Button node`() {
        val node = button(label = "Submit")
        assertNode(node).hasType("Button").hasText("Submit")
        assertNode(node).hasMeta("enabled", "true")
    }

    @Test
    fun `disabled button has enabled false`() {
        val node = button(label = "Submit", enabled = false)
        assertNode(node).hasMeta("enabled", "false")
    }

    @Test
    fun `divider creates Divider node`() {
        val node = divider()
        assertNode(node).hasType("Divider")
    }

    @Test
    fun `spacer creates Spacer node`() {
        val node = spacer()
        assertNode(node).hasType("Spacer")
    }
}
