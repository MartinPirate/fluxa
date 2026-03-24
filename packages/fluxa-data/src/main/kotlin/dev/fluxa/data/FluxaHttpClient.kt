package dev.fluxa.data

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI

/**
 * HTTP client with interceptor chain support. Wraps [HttpURLConnection]
 * for zero external dependencies. Interceptors can add auth headers,
 * retry failed requests, log traffic, or transform requests/responses.
 *
 * For production apps with advanced needs (HTTP/2, connection pooling),
 * swap in an adapter backed by OkHttp or Ktor.
 */
class FluxaHttpClient(
    private val baseUrl: String = "",
    private val defaultHeaders: Map<String, String> = emptyMap(),
    private val timeoutMs: Int = 15_000,
    private val interceptors: List<FluxaInterceptor> = emptyList(),
) {
    /** Execute a GET request. */
    suspend fun get(path: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        executeWithChain("GET", path, headers, body = null)

    /** Execute a POST request with a JSON body. */
    suspend fun post(path: String, body: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        executeWithChain("POST", path, headers + ("Content-Type" to "application/json"), body)

    /** Execute a PUT request with a JSON body. */
    suspend fun put(path: String, body: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        executeWithChain("PUT", path, headers + ("Content-Type" to "application/json"), body)

    /** Execute a DELETE request. */
    suspend fun delete(path: String, headers: Map<String, String> = emptyMap()): FluxaHttpResponse =
        executeWithChain("DELETE", path, headers, body = null)

    private suspend fun executeWithChain(
        method: String,
        path: String,
        headers: Map<String, String>,
        body: String?,
    ): FluxaHttpResponse {
        val request = FluxaRequest(
            method = method,
            url = "$baseUrl$path",
            headers = defaultHeaders + headers,
            body = body,
        )

        val terminalChain = FluxaChain { req -> executeRaw(req) }

        val chain = interceptors.foldRight(terminalChain) { interceptor, next ->
            FluxaChain { req -> interceptor.intercept(req, next) }
        }

        return chain.proceed(request)
    }

    private suspend fun executeRaw(request: FluxaRequest): FluxaHttpResponse {
        return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            val url = URI(request.url).toURL()
            val conn = url.openConnection() as HttpURLConnection
            try {
                conn.requestMethod = request.method
                conn.connectTimeout = timeoutMs
                conn.readTimeout = timeoutMs

                request.headers.forEach { (k, v) -> conn.setRequestProperty(k, v) }

                if (request.body != null) {
                    conn.doOutput = true
                    conn.outputStream.bufferedWriter().use { it.write(request.body) }
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

/**
 * HTTP response with status code and body.
 */
data class FluxaHttpResponse(
    val statusCode: Int,
    val body: String,
) {
    /** True if the status code is in the 2xx range. */
    val isSuccess: Boolean get() = statusCode in 200..299

    /** Parse the body into a [FluxaResource]. */
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
