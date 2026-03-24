package dev.fluxa.nav

import dev.fluxa.state.FluxaState

/**
 * Observable back stack that drives navigation state.
 */
class FluxaBackStack(initial: FluxaRoute) {
    private val stack = FluxaState(listOf(initial))

    val entries: List<FluxaRoute> get() = stack.value
    val current: FluxaRoute get() = stack.value.last()
    val canGoBack: Boolean get() = stack.value.size > 1

    fun observe(listener: (List<FluxaRoute>) -> Unit): () -> Unit = stack.observe(listener)

    fun push(route: FluxaRoute) {
        stack.update { it + route }
    }

    fun pop(): Boolean {
        if (!canGoBack) return false
        stack.update { it.dropLast(1) }
        return true
    }

    fun popTo(route: FluxaRoute, inclusive: Boolean = false): Boolean {
        val current = stack.value
        val index = current.indexOfLast { it == route }
        if (index < 0) return false

        val target = if (inclusive) index else index + 1
        if (target >= current.size) return false

        stack.update { it.take(target.coerceAtLeast(1)) }
        return true
    }

    fun replace(route: FluxaRoute) {
        stack.update { it.dropLast(1) + route }
    }

    fun replaceAll(route: FluxaRoute) {
        stack.set(listOf(route))
    }
}
