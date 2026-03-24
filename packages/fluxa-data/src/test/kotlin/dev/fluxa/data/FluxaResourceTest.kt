package dev.fluxa.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class FluxaResourceTest {

    @Test
    fun `idle state`() {
        val r: FluxaResource<String> = FluxaResource.Idle
        assertFalse(r.isLoading)
        assertFalse(r.isSuccess)
        assertFalse(r.isError)
        assertNull(r.dataOrNull())
    }

    @Test
    fun `loading state`() {
        val r: FluxaResource<String> = FluxaResource.Loading
        assertTrue(r.isLoading)
        assertNull(r.dataOrNull())
    }

    @Test
    fun `success state`() {
        val r = FluxaResource.Success("hello")
        assertTrue(r.isSuccess)
        assertEquals("hello", r.dataOrNull())
    }

    @Test
    fun `error state`() {
        val r = FluxaResource.Error("fail")
        assertTrue(r.isError)
        assertEquals("fail", r.errorOrNull())
    }

    @Test
    fun `map transforms success`() {
        val r = FluxaResource.Success(5).map { it * 2 }
        assertEquals(10, r.dataOrNull())
    }

    @Test
    fun `map preserves error`() {
        val r: FluxaResource<Int> = FluxaResource.Error("fail")
        val mapped = r.map { it * 2 }
        assertTrue(mapped.isError)
    }

    @Test
    fun `map preserves loading`() {
        val r: FluxaResource<Int> = FluxaResource.Loading
        val mapped = r.map { it * 2 }
        assertTrue(mapped.isLoading)
    }

    @Test
    fun `flatMap chains success`() {
        val r = FluxaResource.Success(5).flatMap { FluxaResource.Success(it + 1) }
        assertEquals(6, r.dataOrNull())
    }

    @Test
    fun `flatMap preserves error`() {
        val r: FluxaResource<Int> = FluxaResource.Error("fail")
        val result = r.flatMap { FluxaResource.Success(it + 1) }
        assertTrue(result.isError)
    }
}
