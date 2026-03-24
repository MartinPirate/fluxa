package dev.fluxa.demo

import dev.fluxa.nav.FluxaDeepLink
import dev.fluxa.nav.SimpleRoute
import dev.fluxa.nav.FluxaRoute

object Routes {
    object Home : SimpleRoute("home")
    data class NoteDetail(val noteId: String) : FluxaRoute("note/$noteId") {
        override val args get() = mapOf("noteId" to noteId)
    }
    object CreateNote : SimpleRoute("create")
    object Settings : SimpleRoute("settings")

    val deepLinks = listOf(
        FluxaDeepLink("note/{noteId}") { params ->
            NoteDetail(params["noteId"] ?: "")
        },
    )
}
