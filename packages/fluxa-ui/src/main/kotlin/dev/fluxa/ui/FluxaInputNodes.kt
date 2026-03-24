package dev.fluxa.ui

import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.styleOf

data class FluxaInputConfig(
    val inputType: String,
    val placeholder: String = "",
    val label: String = "",
    val enabled: Boolean = true,
    val checked: Boolean = false,
)

fun textField(
    label: String = "",
    placeholder: String = "",
    style: FluxaStyle = styleOf(),
    enabled: Boolean = true,
): FluxaNode = FluxaNode(
    type = "TextField",
    text = label,
    style = style,
    meta = mapOf(
        "inputType" to "text",
        "placeholder" to placeholder,
        "enabled" to enabled.toString(),
    ),
)

fun toggle(
    label: String = "",
    checked: Boolean = false,
    style: FluxaStyle = styleOf(),
    enabled: Boolean = true,
): FluxaNode = FluxaNode(
    type = "Toggle",
    text = label,
    style = style,
    meta = mapOf(
        "checked" to checked.toString(),
        "enabled" to enabled.toString(),
    ),
)

fun checkbox(
    label: String = "",
    checked: Boolean = false,
    style: FluxaStyle = styleOf(),
    enabled: Boolean = true,
): FluxaNode = FluxaNode(
    type = "Checkbox",
    text = label,
    style = style,
    meta = mapOf(
        "checked" to checked.toString(),
        "enabled" to enabled.toString(),
    ),
)

fun button(
    label: String,
    style: FluxaStyle = styleOf(),
    enabled: Boolean = true,
): FluxaNode = FluxaNode(
    type = "Button",
    text = label,
    style = style,
    meta = mapOf(
        "enabled" to enabled.toString(),
    ),
)

fun divider(
    style: FluxaStyle = styleOf(),
): FluxaNode = FluxaNode(
    type = "Divider",
    style = style,
)

fun spacer(
    style: FluxaStyle = styleOf(),
): FluxaNode = FluxaNode(
    type = "Spacer",
    style = style,
)
