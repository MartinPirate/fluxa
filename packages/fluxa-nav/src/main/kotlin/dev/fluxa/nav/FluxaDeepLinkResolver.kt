package dev.fluxa.nav

import android.content.Intent
import android.net.Uri

/**
 * Resolves deep link URIs to typed routes.
 */
class FluxaDeepLinkResolver(
    private val links: List<FluxaDeepLink> = emptyList(),
) {
    /**
     * Try to resolve a URI string to a route.
     */
    fun resolve(uri: String): FluxaRoute? {
        val parsed = Uri.parse(uri)
        for (link in links) {
            val params = matchPattern(link.pattern, parsed) ?: continue
            return link.factory(params)
        }
        return null
    }

    /**
     * Try to resolve an Intent's data URI.
     */
    fun resolve(intent: Intent): FluxaRoute? {
        val data = intent.data ?: return null
        return resolve(data.toString())
    }

    private fun matchPattern(pattern: String, uri: Uri): Map<String, String>? {
        val patternParts = pattern.trimStart('/').split("/")
        val pathParts = uri.path?.trimStart('/')?.split("/") ?: return null

        if (patternParts.size != pathParts.size) return null

        val params = mutableMapOf<String, String>()
        for (i in patternParts.indices) {
            val pp = patternParts[i]
            if (pp.startsWith("{") && pp.endsWith("}")) {
                params[pp.removeSurrounding("{", "}")] = pathParts[i]
            } else if (pp != pathParts[i]) {
                return null
            }
        }

        // Include query parameters
        uri.queryParameterNames.forEach { key ->
            uri.getQueryParameter(key)?.let { params[key] = it }
        }

        return params
    }
}
