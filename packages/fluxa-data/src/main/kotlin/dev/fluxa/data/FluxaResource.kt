package dev.fluxa.data

/**
 * Sealed type representing async data states.
 * Every data fetch in Fluxa should flow through this type.
 */
sealed class FluxaResource<out T> {
    object Idle : FluxaResource<Nothing>()
    object Loading : FluxaResource<Nothing>()
    data class Success<T>(val data: T) : FluxaResource<T>()
    data class Error(val message: String, val cause: Throwable? = null) : FluxaResource<Nothing>()

    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun dataOrNull(): T? = (this as? Success)?.data
    fun errorOrNull(): String? = (this as? Error)?.message

    fun <R> map(transform: (T) -> R): FluxaResource<R> = when (this) {
        is Idle -> Idle
        is Loading -> Loading
        is Success -> Success(transform(data))
        is Error -> this
    }

    fun <R> flatMap(transform: (T) -> FluxaResource<R>): FluxaResource<R> = when (this) {
        is Idle -> Idle
        is Loading -> Loading
        is Success -> transform(data)
        is Error -> this
    }
}
