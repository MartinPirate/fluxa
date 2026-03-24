package dev.fluxa.data

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI

/**
 * Minimal HTTP client abstraction. Wraps java.net.HttpURLConnection
 * so apps don't need OkHttp for simple use cases.
 *
 * For production apps, swap in an adapter backed by OkHttp/Ktor.
 */
class FluxaHttpClient(
    private val baseUrl: String = "",
    private val defaultHeaders: Map<String, String> = emptyMap(),
    private val timeoutMs: Int = 15_000,
) {
    suspend fun get(path: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        execute("GET", path, headers, body = null)

    suspend fun post(path: String, body: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        execute("POST", path, headers + ("Content-Type" to "application/json"), body)

    suspend fun put(path: String, body: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        execute("PUT", path, headers + ("Content-Type" to "application/json"), body)

    suspend fun delete(path: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        execute("DELETE", path, headers, body = null)

    private suspend fun execute(
        method: String,
        path: String,
        headers: Map<String, String>,
        body: String?,
    ): FluxaHttpResponse {
        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            val url = URI("$baseUrl$path").toURL()
            val conn = url.openConnection() as HttpURLConnection
            try {
                conn.requestMethod = method
                conn.connectTimeout = timeoutMs
                conn.readTimeout = timeoutMs

                (defaultHeaders + headers).forEach { (k, v) -> conn.setRequestProperty(k, v) }

                if (body != null) {
                    conn.doOutput = true
                    conn.outputStream.bufferedWriter().use { it.write(body) }
                }

                val code = conn.responseCode
                val responseBody = try {
                    conn.inputStream.bufferedReader().readText()
                } catch (_: IOException) {
                    conn.errorStream?.bufferedReader()?.readText() ?: ""
                }

                FluxaHttpResponse(code, responseBody)
            } finally {
                conn.disconnect()
            }
        }
    }
}

data class FluxaHttpResponse(
    val statusCode: Int,
    val body: String,
) {
    val isSuccess: Boolean get() = statusCode in 200..299

    fun <T> toResource(parse: (String) -> T): FluxaResource<T> {
        return if (isSuccess) {
            try {
                FluxaResource.Success(parse(body))
            } catch (e: Exception) {
                FluxaResource.Error("Parse error: ${e.message}", e)
            }
        } else {
            FluxaResource.Error("HTTP $statusCode: $body")
        }
    }
}
