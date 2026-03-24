package dev.fluxa.test

import dev.fluxa.data.FluxaResource

/**
 * Test assertion DSL for FluxaResource.
 */
class FluxaResourceAssertion<T>(private val resource: FluxaResource<T>) {

    fun isIdle(): FluxaResourceAssertion<T> {
        check(resource is FluxaResource.Idle) {
            "Expected Idle but got $resource"
        }
        return this
    }

    fun isLoading(): FluxaResourceAssertion<T> {
        check(resource is FluxaResource.Loading) {
            "Expected Loading but got $resource"
        }
        return this
    }

    fun isSuccess(): FluxaResourceAssertion<T> {
        check(resource is FluxaResource.Success) {
            "Expected Success but got $resource"
        }
        return this
    }

    fun isError(): FluxaResourceAssertion<T> {
        check(resource is FluxaResource.Error) {
            "Expected Error but got $resource"
        }
        return this
    }

    fun hasData(expected: T): FluxaResourceAssertion<T> {
        val data = (resource as? FluxaResource.Success)?.data
        check(data == expected) {
            "Expected data '$expected' but got '$data'"
        }
        return this
    }

    fun hasError(expected: String): FluxaResourceAssertion<T> {
        val msg = (resource as? FluxaResource.Error)?.message
        check(msg == expected) {
            "Expected error '$expected' but got '$msg'"
        }
        return this
    }

    fun dataMatches(predicate: (T) -> Boolean): FluxaResourceAssertion<T> {
        val data = (resource as? FluxaResource.Success)?.data
            ?: error("Expected Success but got $resource")
        check(predicate(data)) {
            "Data did not match predicate: $data"
        }
        return this
    }
}

fun <T> assertResource(resource: FluxaResource<T>): FluxaResourceAssertion<T> =
    FluxaResourceAssertion(resource)
