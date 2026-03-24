package dev.fluxa.state

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FluxaStoreTest {

    sealed class Action {
        object Increment : Action()
        object Decrement : Action()
        data class Set(val value: Int) : Action()
    }

    private fun createStore(initial: Int = 0) = storeOf<Int, Action>(
        initial = initial,
    ) { state, action ->
        when (action) {
            is Action.Increment -> state + 1
            is Action.Decrement -> state - 1
            is Action.Set -> action.value
        }
    }

    @Test
    fun `initial state is set`() {
        val store = createStore(10)
        assertEquals(10, store.current)
    }

    @Test
    fun `dispatch applies reducer`() {
        val store = createStore()
        store.dispatch(Action.Increment)
        assertEquals(1, store.current)
    }

    @Test
    fun `multiple dispatches accumulate`() {
        val store = createStore()
        store.dispatch(Action.Increment)
        store.dispatch(Action.Increment)
        store.dispatch(Action.Decrement)
        assertEquals(1, store.current)
    }

    @Test
    fun `set action replaces state`() {
        val store = createStore(5)
        store.dispatch(Action.Set(42))
        assertEquals(42, store.current)
    }

    @Test
    fun `observe receives state changes`() {
        val store = createStore()
        val states = mutableListOf<Int>()
        store.observe { states.add(it) }
        store.dispatch(Action.Increment)
        store.dispatch(Action.Set(10))
        assertEquals(listOf(0, 1, 10), states)
    }

    @Test
    fun `middleware intercepts actions`() {
        val log = mutableListOf<String>()
        val middleware = FluxaMiddleware<Int, Action> { state, action, next ->
            log.add("before:$action")
            next(action)
            log.add("after:$action")
        }

        val store = storeOf<Int, Action>(
            initial = 0,
            middleware = listOf(middleware),
        ) { state, action ->
            when (action) {
                is Action.Increment -> state + 1
                else -> state
            }
        }

        store.dispatch(Action.Increment)
        assertEquals(2, log.size)
        assertTrue(log[0].startsWith("before:"))
        assertTrue(log[1].startsWith("after:"))
        assertEquals(1, store.current)
    }

    @Test
    fun `select creates derived selector`() {
        val store = createStore(42)
        val selector = store.select { it * 2 }
        assertEquals(84, selector.value)
    }
}
