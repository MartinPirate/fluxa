package dev.fluxa.data

/**
 * Typed endpoint definition. Pairs a path with a response parser.
 *
 * Example:
 *   val getUsers = FluxaEndpoint.get("/api/users") { body -> parseUsers(body) }
 */
class FluxaEndpoint<T>(
    val method: String,
    val path: String,
    val parse: (String) -> T,
) {
    companion object {
        fun <T> get(path: String, parse: (String) -> T) =
            FluxaEndpoint("GET", path, parse)

        fun <T> post(path: String, parse: (String) -> T) =
            FluxaEndpoint("POST", path, parse)

        fun <T> put(path: String, parse: (String) -> T) =
            FluxaEndpoint("PUT", path, parse)

        fun <T> delete(path: String, parse: (String) -> T) =
            FluxaEndpoint("DELETE", path, parse)
    }
}

/**
 * Execute a typed endpoint against a client.
 */
suspend fun <T> FluxaHttpClient.execute(endpoint: FluxaEndpoint<T>, body: String? = null): FluxaResource<T> {
    val response = when (endpoint.method) {
        "GET" -> get(endpoint.path)
        "POST" -> post(endpoint.path, body ?: "")
        "PUT" -> put(endpoint.path, body ?: "")
        "DELETE" -> delete(endpoint.path)
        else -> return FluxaResource.Error("Unsupported method: ${endpoint.method}")
    }
    return response.toResource(endpoint.parse)
}
