package dev.fluxa.ui

import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.styleOf

data class FluxaNode(
    val type: String,
    val text: String? = null,
    val style: FluxaStyle = styleOf(),
    val activeVariants: Set<FluxaVariant> = emptySet(),
    val children: List<FluxaNode> = emptyList(),
    val meta: Map<String, String> = emptyMap(),
    val semantics: FluxaSemantics? = null,
)

data class FluxaSemantics(
    val contentDescription: String? = null,
    val role: FluxaRole? = null,
    val heading: Boolean = false,
    val disabled: Boolean = false,
    val liveRegion: Boolean = false,
)

enum class FluxaRole {
    BUTTON,
    CHECKBOX,
    SWITCH,
    IMAGE,
    TAB,
    HEADING,
}

fun FluxaNode.withSemantics(
    contentDescription: String? = null,
    role: FluxaRole? = null,
    heading: Boolean = false,
    disabled: Boolean = false,
    liveRegion: Boolean = false,
): FluxaNode = copy(
    semantics = FluxaSemantics(
        contentDescription = contentDescription,
        role = role,
        heading = heading,
        disabled = disabled,
        liveRegion = liveRegion,
    ),
)

fun screen(vararg children: FluxaNode): FluxaNode = FluxaNode(
    type = "Screen",
    children = children.toList(),
)

fun column(style: FluxaStyle = styleOf(), vararg children: FluxaNode): FluxaNode = FluxaNode(
    type = "Column",
    style = style,
    children = children.toList(),
)

fun row(style: FluxaStyle = styleOf(), vararg children: FluxaNode): FluxaNode = FluxaNode(
    type = "Row",
    style = style,
    children = children.toList(),
)

fun stack(style: FluxaStyle = styleOf(), vararg children: FluxaNode): FluxaNode = FluxaNode(
    type = "Stack",
    style = style,
    children = children.toList(),
)

fun text(value: String, style: FluxaStyle = styleOf()): FluxaNode = FluxaNode(
    type = "Text",
    text = value,
    style = style,
)

fun FluxaNode.withVariants(vararg variants: FluxaVariant): FluxaNode = copy(
    activeVariants = activeVariants + variants,
)

fun FluxaNode.debugTree(indent: String = ""): String {
    val styleSummary = buildString {
        if (style.base.isNotEmpty()) {
            append(" base=")
            append(style.base.joinToString(prefix = "[", postfix = "]") { it.javaClass.simpleName })
        }
        if (style.variants.isNotEmpty()) {
            append(" variants=")
            append(style.variants.keys.joinToString(prefix = "[", postfix = "]"))
        }
        if (style.responsive.isNotEmpty()) {
            append(" responsive=")
            append(style.responsive.keys.joinToString(prefix = "[", postfix = "]"))
        }
    }

    val label = text?.let { "$type(\"$it\")" } ?: type
    val header = "$indent- $label$styleSummary"

    if (children.isEmpty()) {
        return header
    }

    return buildString {
        appendLine(header)
        append(children.joinToString(separator = "\n") { child -> child.debugTree("$indent  ") })
    }
}
