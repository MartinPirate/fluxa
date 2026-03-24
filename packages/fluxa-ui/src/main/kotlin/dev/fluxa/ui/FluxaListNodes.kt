package dev.fluxa.ui

import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.styleOf

fun lazyColumn(
    style: FluxaStyle = styleOf(),
    vararg children: FluxaNode,
): FluxaNode = FluxaNode(
    type = "LazyColumn",
    style = style,
    children = children.toList(),
)

fun lazyRow(
    style: FluxaStyle = styleOf(),
    vararg children: FluxaNode,
): FluxaNode = FluxaNode(
    type = "LazyRow",
    style = style,
    children = children.toList(),
)

fun image(
    description: String = "",
    source: String = "",
    style: FluxaStyle = styleOf(),
): FluxaNode = FluxaNode(
    type = "Image",
    text = description,
    style = style,
    meta = mapOf("source" to source),
).withSemantics(
    contentDescription = description,
    role = FluxaRole.IMAGE,
)
