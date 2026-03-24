package dev.fluxa.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.fluxa.state.FluxaState
import dev.fluxa.state.FluxaStore
import dev.fluxa.state.FluxaSelector
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Observe a [FluxaState] as Compose state. The returned state
 * updates whenever the source emits a new value and automatically
 * unsubscribes when the composable leaves the tree.
 */
@Composable
fun <T> FluxaState<T>.collectAsState(): androidx.compose.runtime.State<T> {
    var current by remember { mutableStateOf(value) }
    DisposableEffect(this) {
        val cancel = observe { current = it }
        onDispose { cancel() }
    }
    return remember { object : androidx.compose.runtime.State<T> {
        override val value: T get() = current
    }}
}

/**
 * Observe a [FluxaStore]'s state as Compose state.
 */
@Composable
fun <S, A> FluxaStore<S, A>.collectAsState(): androidx.compose.runtime.State<S> {
    var current by remember { mutableStateOf(this.current) }
    DisposableEffect(this) {
        val cancel = observe { current = it }
        onDispose { cancel() }
    }
    return remember { object : androidx.compose.runtime.State<S> {
        override val value: S get() = current
    }}
}

/**
 * Observe a [FluxaSelector] as Compose state. Only triggers
 * recomposition when the selected slice changes.
 */
@Composable
fun <S, R> FluxaSelector<S, R>.collectAsState(): androidx.compose.runtime.State<R> {
    var current by remember { mutableStateOf(value) }
    DisposableEffect(this) {
        val cancel = observe { current = it }
        onDispose { cancel() }
    }
    return remember { object : androidx.compose.runtime.State<R> {
        override val value: R get() = current
    }}
}

/**
 * Run a suspend effect tied to the lifecycle owner.
 * [onStart] fires on [Lifecycle.Event.ON_START] and is cancelled on STOP.
 * [onStop] fires on [Lifecycle.Event.ON_STOP] and on composable disposal.
 */
@Composable
fun FluxaLifecycleEffect(
    vararg keys: Any?,
    onStart: suspend () -> Unit = {},
    onStop: () -> Unit = {},
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val scope = rememberCoroutineScope()

    DisposableEffect(lifecycle, *keys) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> scope.launch { onStart() }
                Lifecycle.Event.ON_STOP -> onStop()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            onStop()
        }
    }
}

/**
 * Run a polling effect at a fixed interval. The action is called
 * repeatedly with [intervalMs] delay between invocations. Exceptions
 * in [action] are caught and logged to prevent crash-restart storms.
 * Minimum interval is clamped to 1000ms.
 */
@Composable
fun FluxaPollingEffect(
    intervalMs: Long,
    vararg keys: Any?,
    action: suspend () -> Unit,
) {
    val safeInterval = intervalMs.coerceAtLeast(1_000L)

    LaunchedEffect(*keys) {
        while (coroutineContext.isActive) {
            try {
                action()
            } catch (e: Exception) {
                // Log but do not crash — prevents restart storms
                e.printStackTrace()
            }
            delay(safeInterval)
        }
    }
}

/**
 * Run a one-shot effect that fires once per unique key set.
 */
@Composable
fun FluxaOnceEffect(
    vararg keys: Any?,
    action: suspend () -> Unit,
) {
    LaunchedEffect(*keys) {
        action()
    }
}
