package dev.fluxa.test

import dev.fluxa.style.FluxaVariant
import dev.fluxa.ui.FluxaNode

/**
 * Test assertion DSL for FluxaNode trees.
 */
class FluxaNodeAssertion(private val node: FluxaNode) {

    fun hasType(expected: String): FluxaNodeAssertion {
        check(node.type == expected) {
            "Expected node type '$expected' but got '${node.type}'"
        }
        return this
    }

    fun hasText(expected: String): FluxaNodeAssertion {
        check(node.text == expected) {
            "Expected text '$expected' but got '${node.text}'"
        }
        return this
    }

    fun hasChildCount(expected: Int): FluxaNodeAssertion {
        check(node.children.size == expected) {
            "Expected $expected children but got ${node.children.size}"
        }
        return this
    }

    fun hasChildren(): FluxaNodeAssertion {
        check(node.children.isNotEmpty()) {
            "Expected node to have children but it has none"
        }
        return this
    }

    fun hasNoChildren(): FluxaNodeAssertion {
        check(node.children.isEmpty()) {
            "Expected node to have no children but it has ${node.children.size}"
        }
        return this
    }

    fun hasVariant(variant: FluxaVariant): FluxaNodeAssertion {
        check(variant in node.activeVariants) {
            "Expected variant ${variant.name} but active variants are ${node.activeVariants}"
        }
        return this
    }

    fun hasNoVariant(variant: FluxaVariant): FluxaNodeAssertion {
        check(variant !in node.activeVariants) {
            "Expected no variant ${variant.name} but it was active"
        }
        return this
    }

    fun hasMeta(key: String, value: String): FluxaNodeAssertion {
        check(node.meta[key] == value) {
            "Expected meta[$key]='$value' but got '${node.meta[key]}'"
        }
        return this
    }

    fun hasSemantics(): FluxaNodeAssertion {
        check(node.semantics != null) {
            "Expected node to have semantics but it has none"
        }
        return this
    }

    fun hasContentDescription(expected: String): FluxaNodeAssertion {
        check(node.semantics?.contentDescription == expected) {
            "Expected contentDescription '$expected' but got '${node.semantics?.contentDescription}'"
        }
        return this
    }

    fun child(index: Int): FluxaNodeAssertion {
        check(index < node.children.size) {
            "Child index $index out of bounds (${node.children.size} children)"
        }
        return FluxaNodeAssertion(node.children[index])
    }

    fun firstChild(): FluxaNodeAssertion = child(0)
    fun lastChild(): FluxaNodeAssertion = child(node.children.size - 1)

    fun findByType(type: String): FluxaNodeAssertion? {
        val found = node.findFirst { it.type == type }
        return found?.let { FluxaNodeAssertion(it) }
    }

    fun findByText(text: String): FluxaNodeAssertion? {
        val found = node.findFirst { it.text == text }
        return found?.let { FluxaNodeAssertion(it) }
    }

    fun allByType(type: String): List<FluxaNodeAssertion> =
        node.findAll { it.type == type }.map { FluxaNodeAssertion(it) }

    val raw: FluxaNode get() = node
}

fun assertNode(node: FluxaNode): FluxaNodeAssertion = FluxaNodeAssertion(node)

/**
 * Find first node matching a predicate (depth-first).
 */
fun FluxaNode.findFirst(predicate: (FluxaNode) -> Boolean): FluxaNode? {
    if (predicate(this)) return this
    for (child in children) {
        val found = child.findFirst(predicate)
        if (found != null) return found
    }
    return null
}

/**
 * Find all nodes matching a predicate (depth-first).
 */
fun FluxaNode.findAll(predicate: (FluxaNode) -> Boolean): List<FluxaNode> {
    val results = mutableListOf<FluxaNode>()
    fun walk(node: FluxaNode) {
        if (predicate(node)) results.add(node)
        node.children.forEach { walk(it) }
    }
    walk(this)
    return results
}
