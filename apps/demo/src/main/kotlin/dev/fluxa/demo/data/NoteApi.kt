package dev.fluxa.demo.data

import dev.fluxa.cache.FluxaCache
import dev.fluxa.cache.cacheOf
import dev.fluxa.data.FluxaResource
import dev.fluxa.demo.store.Note
import dev.fluxa.demo.store.sampleNotes
import kotlinx.coroutines.delay

/**
 * Simulated API for notes. In a real app this would use FluxaHttpClient
 * to hit a backend. Here we simulate network delay and return sample data.
 */
class NoteApi {
    private val cache: FluxaCache<String, List<Note>> = cacheOf(ttlMs = 60_000)

    suspend fun fetchNotes(): FluxaResource<List<Note>> {
        val cached = cache.get("all")
        if (cached != null) return FluxaResource.Success(cached)

        delay(800) // simulate network
        val notes = sampleNotes
        cache.put("all", notes)
        return FluxaResource.Success(notes)
    }

    suspend fun syncNotes(): FluxaResource<Boolean> {
        delay(1200) // simulate sync
        return FluxaResource.Success(true)
    }

    fun invalidateCache() {
        cache.invalidateAll()
    }
}
