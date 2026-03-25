package dev.fluxa.ui

import dev.fluxa.style.FluxaThemes
import dev.fluxa.test.assertNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FluxaStateComponentsTest {

    private val theme = FluxaThemes.Aurora

    @Test
    fun `LoadingIndicator creates column with dots and message`() {
        val node = LoadingIndicator(theme = theme)
        assertNode(node).hasType("Column")
        // Row with 3 spinner dots + text message
        assertNode(node).hasChildCount(2)
        assertNode(node).firstChild().hasType("Row").hasChildCount(3)
        assertNode(node).child(1).hasType("Text").hasText("Loading…")
    }

    @Test
    fun `LoadingIndicator custom message`() {
        val node = LoadingIndicator(message = "Fetching notes...", theme = theme)
        assertNode(node).child(1).hasText("Fetching notes...")
    }

    @Test
    fun `EmptyState creates column with icon title and message`() {
        val node = EmptyState(
            title = "No notes",
            message = "Create your first note",
            theme = theme,
        )
        assertNode(node).hasType("Column")
        // Icon stack + title + message = 3 children
        assertNode(node).hasChildCount(3)
        assertNode(node).firstChild().hasType("Stack") // icon
        assertNode(node).child(1).hasType("Text").hasText("No notes")
        assertNode(node).child(2).hasType("Text").hasText("Create your first note")
    }

    @Test
    fun `EmptyState without message has 2 children`() {
        val node = EmptyState(title = "Empty", theme = theme)
        assertNode(node).hasChildCount(2) // icon + title only
    }

    @Test
    fun `EmptyState with action button has action as child`() {
        val node = EmptyState(
            title = "Empty",
            theme = theme,
            action = button(label = "Add"),
        )
        assertNode(node).hasChildCount(3) // icon + title + action
        assertNode(node).child(2).hasType("Button").hasText("Add")
    }

    @Test
    fun `ErrorState creates column with icon title and message`() {
        val node = ErrorState(
            title = "Network error",
            message = "Check your connection",
            theme = theme,
        )
        assertNode(node).hasType("Column")
        // Icon + title + message = 3 children
        assertNode(node).hasChildCount(3)
        assertNode(node).firstChild().hasType("Stack") // error icon
        assertNode(node).child(1).hasType("Text").hasText("Network error")
        assertNode(node).child(2).hasType("Text").hasText("Check your connection")
    }

    @Test
    fun `ErrorState with retry adds button`() {
        var retried = false
        val node = ErrorState(
            title = "Failed",
            theme = theme,
            onRetry = { retried = true },
        )
        // Icon + title + retry button = 3 children
        assertNode(node).hasChildCount(3)
        assertNode(node).child(2).hasType("Button").hasText("Retry")
        // Verify handler is wired
        node.children[2].handlers.onClick?.invoke()
        assertTrue(retried)
    }

    @Test
    fun `ErrorState without retry has no button`() {
        val node = ErrorState(title = "Failed", theme = theme)
        assertNode(node).hasChildCount(2) // icon + title
    }

    @Test
    fun `SuccessState creates column with icon and title`() {
        val node = SuccessState(title = "Saved!", theme = theme)
        assertNode(node).hasType("Column")
        assertNode(node).hasChildCount(2) // icon + title
        assertNode(node).child(1).hasType("Text").hasText("Saved!")
    }

    @Test
    fun `SuccessState with message has 3 children`() {
        val node = SuccessState(
            title = "Done",
            message = "Your note was saved",
            theme = theme,
        )
        assertNode(node).hasChildCount(3)
        assertNode(node).child(2).hasType("Text").hasText("Your note was saved")
    }

    @Test
    fun `spinner dots have meta with index`() {
        val node = LoadingIndicator(theme = theme)
        val dots = node.children[0] // the Row
        assertEquals("0", dots.children[0].meta["spinnerIndex"])
        assertEquals("1", dots.children[1].meta["spinnerIndex"])
        assertEquals("2", dots.children[2].meta["spinnerIndex"])
    }
}
