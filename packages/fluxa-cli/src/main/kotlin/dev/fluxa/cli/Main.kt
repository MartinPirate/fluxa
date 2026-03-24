package dev.fluxa.cli

import dev.fluxa.style.FluxaAxisScale
import dev.fluxa.style.FluxaStyles
import dev.fluxa.style.compile
import dev.fluxa.style.style
import dev.fluxa.ui.column
import dev.fluxa.ui.debugTree
import dev.fluxa.ui.screen
import dev.fluxa.ui.text

fun main() {
    println("Fluxa CLI")
    println("Scaffolding is not implemented yet.")
    println()

    val heroStyle = style {
        padding(FluxaAxisScale.XL)
        gap(FluxaAxisScale.MD)
        typography("type.hero")
    }

    val cardStyle = FluxaStyles.surfaceCard()
    val cardSpec = cardStyle.compile()

    val app = screen(
        column(
            style = heroStyle,
            text("Fluxa"),
            text("Build mobile UI with clearer primitives and faster styling."),
        ),
        column(
            style = cardStyle,
            text("Surface card"),
            text("Pressed and responsive variants are defined in the style spec."),
        ),
    )

    println("Sample tree:")
    println(app.debugTree())
    println()
    println("Compiled style spec:")
    println(cardSpec)
}
