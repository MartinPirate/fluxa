package dev.fluxa.state

/**
 * Unidirectional store with typed actions and a pure reducer.
 * Inspired by Redux/MVI but simpler.
 */
class FluxaStore<S, A>(
    initial: S,
    private val reducer: (S, A) -> S,
    private val middleware: List<FluxaMiddleware<S, A>> = emptyList(),
) {
    private val state = FluxaState(initial)

    val current: S get() = state.value

    fun dispatch(action: A) {
        val chain = middleware.foldRight<FluxaMiddleware<S, A>, (A) -> Unit>({ a ->
            state.update { reducer(it, a) }
        }) { mw, next ->
            { a -> mw.intercept(state.value, a, next) }
        }
        chain(action)
    }

    fun observe(listener: (S) -> Unit): () -> Unit = state.observe(listener)

    fun <R> select(selector: (S) -> R): FluxaSelector<S, R> = FluxaSelector(state, selector)
}

fun interface FluxaMiddleware<S, A> {
    fun intercept(state: S, action: A, next: (A) -> Unit)
}

/**
 * Logging middleware for development.
 */
class FluxaLogMiddleware<S, A>(
    private val tag: String = "FluxaStore",
    private val log: (String) -> Unit = ::println,
) : FluxaMiddleware<S, A> {
    override fun intercept(state: S, action: A, next: (A) -> Unit) {
        log("[$tag] action=$action state=$state")
        next(action)
    }
}

fun <S, A> storeOf(
    initial: S,
    middleware: List<FluxaMiddleware<S, A>> = emptyList(),
    reducer: (S, A) -> S,
): FluxaStore<S, A> = FluxaStore(initial, reducer, middleware)
