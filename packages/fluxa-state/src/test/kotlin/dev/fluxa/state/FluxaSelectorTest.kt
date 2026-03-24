package dev.fluxa.state

import kotlin.test.Test
import kotlin.test.assertEquals

class FluxaSelectorTest {

    @Test
    fun `selector returns derived value`() {
        val state = stateOf(10)
        val selector = FluxaSelector(state) { it * 3 }
        assertEquals(30, selector.value)
    }

    @Test
    fun `selector updates when source changes`() {
        val state = stateOf(5)
        val selector = FluxaSelector(state) { it + 1 }
        assertEquals(6, selector.value)

        state.set(10)
        assertEquals(11, selector.value)
    }

    @Test
    fun `selector observe emits on change`() {
        val state = stateOf("hello")
        val selector = FluxaSelector(state) { it.length }
        val values = mutableListOf<Int>()

        val cancel = selector.observe { values.add(it) }
        state.set("hi")
        state.set("hey")

        cancel()
        assertEquals(listOf(5, 2, 3), values)
    }

    @Test
    fun `selector does not emit when derived value unchanged`() {
        val state = stateOf(4)
        val selector = FluxaSelector(state) { it / 2 }
        val values = mutableListOf<Int>()

        selector.observe { values.add(it) }
        state.set(5) // 5/2 = 2 (same as 4/2 = 2)
        state.set(6) // 6/2 = 3 (changed)

        assertEquals(listOf(2, 3), values)
    }
}
