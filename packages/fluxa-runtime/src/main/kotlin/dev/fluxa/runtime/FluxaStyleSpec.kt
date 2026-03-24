package dev.fluxa.runtime

data class FluxaStyleSpec(
    val base: List<FluxaStyleInstruction> = emptyList(),
    val tokens: Map<String, String> = emptyMap(),
    val variants: Map<String, List<FluxaStyleInstruction>> = emptyMap(),
    val responsive: Map<FluxaBreakpoint, List<FluxaStyleInstruction>> = emptyMap(),
)

enum class FluxaBreakpoint {
    COMPACT,
    MEDIUM,
    EXPANDED,
}

data class FluxaStyleInstruction(
    val name: String,
    val value: String,
)

