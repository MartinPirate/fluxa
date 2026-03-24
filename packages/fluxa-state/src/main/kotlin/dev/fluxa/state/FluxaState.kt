package dev.fluxa.state

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference

/**
 * Core observable state holder. Thread-safe, snapshot-based.
 */
class FluxaState<T>(initial: T) {
    private val ref = AtomicReference(initial)
    private val listeners = CopyOnWriteArrayList<(T) -> Unit>()

    val value: T get() = ref.get()

    fun update(transform: (T) -> T) {
        var prev: T
        var next: T
        do {
            prev = ref.get()
            next = transform(prev)
        } while (!ref.compareAndSet(prev, next))
        listeners.forEach { it(next) }
    }

    fun set(value: T) = update { value }

    fun observe(listener: (T) -> Unit): () -> Unit {
        listeners.add(listener)
        listener(ref.get())
        return { listeners.remove(listener) }
    }
}

fun <T> stateOf(initial: T): FluxaState<T> = FluxaState(initial)
