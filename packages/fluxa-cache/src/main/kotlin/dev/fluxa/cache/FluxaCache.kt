package dev.fluxa.cache

import java.util.concurrent.ConcurrentHashMap

/**
 * In-memory cache with TTL and manual invalidation.
 */
class FluxaCache<K, V>(
    private val defaultTtlMs: Long = 5 * 60 * 1000L,
    private val maxSize: Int = 256,
) {
    private data class Entry<V>(
        val value: V,
        val expiresAt: Long,
    )

    private val store = ConcurrentHashMap<K, Entry<V>>()

    fun get(key: K): V? {
        val entry = store[key] ?: return null
        if (System.currentTimeMillis() > entry.expiresAt) {
            store.remove(key)
            return null
        }
        return entry.value
    }

    fun put(key: K, value: V, ttlMs: Long = defaultTtlMs) {
        evictIfNeeded()
        store[key] = Entry(value, System.currentTimeMillis() + ttlMs)
    }

    fun getOrPut(key: K, ttlMs: Long = defaultTtlMs, compute: () -> V): V {
        val existing = get(key)
        if (existing != null) return existing
        val value = compute()
        put(key, value, ttlMs)
        return value
    }

    fun invalidate(key: K) {
        store.remove(key)
    }

    fun invalidateAll() {
        store.clear()
    }

    fun invalidateWhere(predicate: (K) -> Boolean) {
        store.keys.filter(predicate).forEach { store.remove(it) }
    }

    val size: Int get() = store.size

    val keys: Set<K> get() = store.keys.toSet()

    private fun evictIfNeeded() {
        if (store.size < maxSize) return

        // Remove expired entries first
        val now = System.currentTimeMillis()
        store.entries.removeIf { it.value.expiresAt < now }

        // If still over limit, remove oldest entries
        if (store.size >= maxSize) {
            val toRemove = store.entries
                .sortedBy { it.value.expiresAt }
                .take(store.size - maxSize + 1)
            toRemove.forEach { store.remove(it.key) }
        }
    }
}

/**
 * Create a string-keyed cache with sensible defaults.
 */
fun <V> cacheOf(
    ttlMs: Long = 5 * 60 * 1000L,
    maxSize: Int = 256,
): FluxaCache<String, V> = FluxaCache(ttlMs, maxSize)
