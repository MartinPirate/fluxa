package dev.fluxa.nav

/**
 * A typed route definition. Subclass to define app routes.
 *
 * Example:
 *   object Home : FluxaRoute("home")
 *   data class Profile(val userId: String) : FluxaRoute("profile/$userId")
 */
abstract class FluxaRoute(val path: String) {
    open val args: Map<String, String> get() = emptyMap()

    override fun toString(): String = path
    override fun equals(other: Any?): Boolean = other is FluxaRoute && other.path == path && other.args == args
    override fun hashCode(): Int = path.hashCode() * 31 + args.hashCode()
}

/**
 * A simple route with no arguments.
 */
open class SimpleRoute(path: String) : FluxaRoute(path)

/**
 * Deep link mapping — associates a URI pattern with a route factory.
 */
data class FluxaDeepLink(
    val pattern: String,
    val factory: (Map<String, String>) -> FluxaRoute,
)
