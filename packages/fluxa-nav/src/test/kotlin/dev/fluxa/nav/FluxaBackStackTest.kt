package dev.fluxa.nav

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class FluxaBackStackTest {

    object Home : SimpleRoute("home")
    object Settings : SimpleRoute("settings")
    object Profile : SimpleRoute("profile")

    @Test
    fun `initial state has one entry`() {
        val stack = FluxaBackStack(Home)
        assertEquals(Home, stack.current)
        assertEquals(1, stack.entries.size)
        assertFalse(stack.canGoBack)
    }

    @Test
    fun `push adds entry`() {
        val stack = FluxaBackStack(Home)
        stack.push(Settings)
        assertEquals(Settings, stack.current)
        assertEquals(2, stack.entries.size)
        assertTrue(stack.canGoBack)
    }

    @Test
    fun `pop removes last entry`() {
        val stack = FluxaBackStack(Home)
        stack.push(Settings)
        val popped = stack.pop()
        assertTrue(popped)
        assertEquals(Home, stack.current)
        assertFalse(stack.canGoBack)
    }

    @Test
    fun `pop on single entry returns false`() {
        val stack = FluxaBackStack(Home)
        assertFalse(stack.pop())
        assertEquals(Home, stack.current)
    }

    @Test
    fun `replace swaps top entry`() {
        val stack = FluxaBackStack(Home)
        stack.push(Settings)
        stack.replace(Profile)
        assertEquals(Profile, stack.current)
        assertEquals(2, stack.entries.size)
    }

    @Test
    fun `replaceAll resets to single entry`() {
        val stack = FluxaBackStack(Home)
        stack.push(Settings)
        stack.push(Profile)
        stack.replaceAll(Home)
        assertEquals(Home, stack.current)
        assertEquals(1, stack.entries.size)
    }

    @Test
    fun `popTo navigates back to target`() {
        val stack = FluxaBackStack(Home)
        stack.push(Settings)
        stack.push(Profile)
        val result = stack.popTo(Home)
        assertTrue(result)
        assertEquals(Home, stack.current)
    }

    @Test
    fun `popTo with inclusive removes target too`() {
        val stack = FluxaBackStack(Home)
        stack.push(Settings)
        stack.push(Profile)
        // popTo Home inclusive should leave just Home (can't remove last)
        stack.popTo(Settings, inclusive = true)
        assertEquals(Home, stack.current)
    }

    @Test
    fun `observe receives updates`() {
        val stack = FluxaBackStack(Home)
        val snapshots = mutableListOf<List<FluxaRoute>>()
        stack.observe { snapshots.add(it) }
        stack.push(Settings)
        assertTrue(snapshots.size >= 2)
    }
}
