package dev.fluxa.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.fluxa.state.FluxaState
import dev.fluxa.state.FluxaStore
import dev.fluxa.state.FluxaSelector
import kotlinx.coroutines.delay

/**
 * Observe a FluxaState as Compose state.
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
 * Observe a FluxaStore's state as Compose state.
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
 * Observe a FluxaSelector as Compose state.
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
 * Run a suspend effect tied to lifecycle.
 * Cancels on STOPPED, restarts on STARTED.
 */
@Composable
fun FluxaLifecycleEffect(
    vararg keys: Any?,
    onStart: suspend () -> Unit = {},
    onStop: () -> Unit = {},
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle, *keys) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> onStop()
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(lifecycle, *keys) {
        onStart()
    }
}

/**
 * Run a polling effect at a fixed interval.
 */
@Composable
fun FluxaPollingEffect(
    intervalMs: Long,
    vararg keys: Any?,
    action: suspend () -> Unit,
) {
    LaunchedEffect(keys) {
        while (true) {
            action()
            delay(intervalMs)
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
    LaunchedEffect(keys) {
        action()
    }
}
