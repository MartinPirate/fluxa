package dev.fluxa.state

/**
 * Derived value from a state source. Only notifies when the
 * selected slice actually changes.
 */
class FluxaSelector<S, R>(
    private val source: FluxaState<S>,
    private val selector: (S) -> R,
) {
    val value: R get() = selector(source.value)

    fun observe(listener: (R) -> Unit): () -> Unit {
        var last = selector(source.value)
        listener(last)
        return source.observe { state ->
            val next = selector(state)
            if (next != last) {
                last = next
                listener(next)
            }
        }
    }
}

/**
 * Combine two selectors into a derived pair.
 */
fun <S, A, B> combine(
    a: FluxaSelector<S, A>,
    b: FluxaSelector<S, B>,
): FluxaSelector<S, Pair<A, B>> {
    // Both selectors must share the same source — this is a type-level contract.
    // For safety, we expose a combined observe pattern.
    @Suppress("UNCHECKED_CAST")
    val source = try {
        val field = FluxaSelector::class.java.getDeclaredField("source")
        field.isAccessible = true
        field.get(a) as FluxaState<S>
    } catch (_: Exception) {
        throw IllegalStateException("Cannot combine selectors from different sources")
    }

    return FluxaSelector(source) { state ->
        Pair(a.value, b.value)
    }
}
