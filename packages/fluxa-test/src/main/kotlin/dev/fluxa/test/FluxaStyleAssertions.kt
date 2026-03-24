package dev.fluxa.test

import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.compile

/**
 * Test assertion DSL for FluxaStyle.
 */
class FluxaStyleAssertion(private val style: FluxaStyle) {
    private val spec = style.compile()

    fun hasInstruction(name: String): FluxaStyleAssertion {
        check(spec.base.any { it.name == name }) {
            "Expected instruction '$name' in base but found: ${spec.base.map { it.name }}"
        }
        return this
    }

    fun hasInstruction(name: String, value: String): FluxaStyleAssertion {
        check(spec.base.any { it.name == name && it.value == value }) {
            val actual = spec.base.filter { it.name == name }.map { it.value }
            "Expected instruction '$name=$value' but found: $actual"
        }
        return this
    }

    fun hasNoInstruction(name: String): FluxaStyleAssertion {
        check(spec.base.none { it.name == name }) {
            "Expected no instruction '$name' but it was present"
        }
        return this
    }

    fun hasVariant(variant: FluxaVariant): FluxaStyleAssertion {
        val key = variant.name.lowercase()
        check(key in spec.variants) {
            "Expected variant '$key' but found: ${spec.variants.keys}"
        }
        return this
    }

    fun hasNoVariant(variant: FluxaVariant): FluxaStyleAssertion {
        val key = variant.name.lowercase()
        check(key !in spec.variants) {
            "Expected no variant '$key' but it was present"
        }
        return this
    }

    fun hasResponsive(breakpoint: FluxaBreakpoint): FluxaStyleAssertion {
        check(breakpoint in spec.responsive) {
            "Expected responsive rule for $breakpoint but found: ${spec.responsive.keys}"
        }
        return this
    }

    fun variantHasInstruction(variant: FluxaVariant, name: String, value: String): FluxaStyleAssertion {
        val key = variant.name.lowercase()
        val instructions = spec.variants[key] ?: error("No variant '$key' found")
        check(instructions.any { it.name == name && it.value == value }) {
            val actual = instructions.filter { it.name == name }.map { it.value }
            "Expected variant '$key' instruction '$name=$value' but found: $actual"
        }
        return this
    }

    fun instructionCount(): Int = spec.base.size
}

fun assertStyle(style: FluxaStyle): FluxaStyleAssertion = FluxaStyleAssertion(style)
