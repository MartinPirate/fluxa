package dev.fluxa.state

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class FluxaStateTest {

    @Test
    fun `initial value is accessible`() {
        val state = stateOf(42)
        assertEquals(42, state.value)
    }

    @Test
    fun `set replaces value`() {
        val state = stateOf("hello")
        state.set("world")
        assertEquals("world", state.value)
    }

    @Test
    fun `update transforms value`() {
        val state = stateOf(10)
        state.update { it + 5 }
        assertEquals(15, state.value)
    }

    @Test
    fun `observe emits current value immediately`() {
        val state = stateOf(99)
        var received = 0
        state.observe { received = it }
        assertEquals(99, received)
    }

    @Test
    fun `observe receives updates`() {
        val state = stateOf(0)
        val values = mutableListOf<Int>()
        state.observe { values.add(it) }
        state.set(1)
        state.set(2)
        assertEquals(listOf(0, 1, 2), values)
    }

    @Test
    fun `unsubscribe stops notifications`() {
        val state = stateOf(0)
        val values = mutableListOf<Int>()
        val cancel = state.observe { values.add(it) }
        state.set(1)
        cancel()
        state.set(2)
        assertEquals(listOf(0, 1), values)
    }

    @Test
    fun `concurrent updates are safe`() {
        val state = stateOf(0)
        val count = 1000
        val latch = CountDownLatch(count)
        val pool = Executors.newFixedThreadPool(8)

        repeat(count) {
            pool.submit {
                state.update { it + 1 }
                latch.countDown()
            }
        }

        latch.await()
        pool.shutdown()
        assertEquals(count, state.value)
    }
}
