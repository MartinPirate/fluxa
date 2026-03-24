package dev.fluxa.data

import kotlinx.coroutines.delay

/**
 * An HTTP request before it is sent. Interceptors can modify this.
 */
data class FluxaRequest(
    val method: String,
    val url: String,
    val headers: Map<String, String>,
    val body: String?,
)

/**
 * Interceptor chain — each interceptor can modify the request,
 * delegate to the next in the chain, and modify the response.
 */
fun interface FluxaInterceptor {
    suspend fun intercept(request: FluxaRequest, chain: FluxaChain): FluxaHttpResponse
}

/**
 * Represents the rest of the interceptor chain.
 */
fun interface FluxaChain {
    suspend fun proceed(request: FluxaRequest): FluxaHttpResponse
}

/**
 * Provides auth tokens for requests.
 */
fun interface FluxaAuthProvider {
    suspend fun token(): String?
}

/**
 * Bearer token auth interceptor. Injects `Authorization: Bearer <token>`
 * on every request when the provider returns a non-null token.
 */
class FluxaBearerAuth(private val provider: FluxaAuthProvider) : FluxaInterceptor {
    override suspend fun intercept(request: FluxaRequest, chain: FluxaChain): FluxaHttpResponse {
        val token = provider.token()
        val authedRequest = if (token != null) {
            request.copy(headers = request.headers + ("Authorization" to "Bearer $token"))
        } else {
            request
        }
        return chain.proceed(authedRequest)
    }
}

/**
 * Retry interceptor with exponential backoff. Retries on network
 * errors and configurable HTTP status codes.
 */
class FluxaRetryInterceptor(
    private val maxRetries: Int = 3,
    private val retryableStatuses: Set<Int> = setOf(429, 502, 503, 504),
    private val baseDelayMs: Long = 500,
) : FluxaInterceptor {
    override suspend fun intercept(request: FluxaRequest, chain: FluxaChain): FluxaHttpResponse {
        var lastResponse: FluxaHttpResponse? = null
        var lastException: Exception? = null

        for (attempt in 0..maxRetries) {
            try {
                val response = chain.proceed(request)
                if (response.statusCode !in retryableStatuses || attempt == maxRetries) {
                    return response
                }
                lastResponse = response
            } catch (e: Exception) {
                if (attempt == maxRetries) {
                    lastException = e
                    break
                }
            }

            if (attempt < maxRetries) {
                delay(baseDelayMs * (1L shl attempt))
            }
        }

        return lastResponse ?: FluxaHttpResponse(
            statusCode = 0,
            body = "Request failed after ${maxRetries + 1} attempts: ${lastException?.message ?: "unknown error"}",
        )
    }
}

/**
 * Logging interceptor for development. Logs request method, URL,
 * and response status code.
 */
class FluxaLoggingInterceptor(
    private val log: (String) -> Unit = ::println,
) : FluxaInterceptor {
    override suspend fun intercept(request: FluxaRequest, chain: FluxaChain): FluxaHttpResponse {
        log("--> ${request.method} ${request.url}")
        val response = chain.proceed(request)
        log("<-- ${response.statusCode} ${request.url}")
        return response
    }
}
