package dev.fluxa.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FluxaCacheTest {

    @Test
    fun `put and get returns value`() {
        val cache = cacheOf<String>()
        cache.put("key", "value")
        assertEquals("value", cache.get("key"))
    }

    @Test
    fun `get returns null for missing key`() {
        val cache = cacheOf<String>()
        assertNull(cache.get("missing"))
    }

    @Test
    fun `invalidate removes entry`() {
        val cache = cacheOf<String>()
        cache.put("key", "value")
        cache.invalidate("key")
        assertNull(cache.get("key"))
    }

    @Test
    fun `invalidateAll clears cache`() {
        val cache = cacheOf<String>()
        cache.put("a", "1")
        cache.put("b", "2")
        cache.invalidateAll()
        assertEquals(0, cache.size)
    }

    @Test
    fun `invalidateWhere removes matching entries`() {
        val cache = cacheOf<String>()
        cache.put("user:1", "alice")
        cache.put("user:2", "bob")
        cache.put("post:1", "hello")
        cache.invalidateWhere { it.startsWith("user:") }
        assertNull(cache.get("user:1"))
        assertNull(cache.get("user:2"))
        assertEquals("hello", cache.get("post:1"))
    }

    @Test
    fun `getOrPut computes on miss`() {
        val cache = cacheOf<Int>()
        val result = cache.getOrPut("key") { 42 }
        assertEquals(42, result)
    }

    @Test
    fun `getOrPut returns cached on hit`() {
        val cache = cacheOf<Int>()
        cache.put("key", 1)
        val result = cache.getOrPut("key") { 99 }
        assertEquals(1, result)
    }

    @Test
    fun `expired entries are evicted on get`() {
        val cache = FluxaCache<String, String>(defaultTtlMs = 1)
        cache.put("key", "value")
        Thread.sleep(10)
        assertNull(cache.get("key"))
    }

    @Test
    fun `max size triggers eviction`() {
        val cache = FluxaCache<String, String>(maxSize = 3)
        cache.put("a", "1")
        cache.put("b", "2")
        cache.put("c", "3")
        cache.put("d", "4")
        assertTrue(cache.size <= 3)
    }

    @Test
    fun `keys returns current keys`() {
        val cache = cacheOf<String>()
        cache.put("x", "1")
        cache.put("y", "2")
        assertTrue(cache.keys.containsAll(setOf("x", "y")))
    }
}
