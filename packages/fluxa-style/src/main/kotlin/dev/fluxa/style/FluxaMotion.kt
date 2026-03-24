package dev.fluxa.style

enum class FluxaDuration(val ms: Int) {
    INSTANT(0),
    FAST(100),
    NORMAL(200),
    SLOW(350),
    ENTER(250),
    EXIT(200),
}

enum class FluxaEasing {
    LINEAR,
    EASE_IN,
    EASE_OUT,
    EASE_IN_OUT,
    SPRING,
}

data class FluxaTransition(
    val property: String,
    val duration: FluxaDuration = FluxaDuration.NORMAL,
    val easing: FluxaEasing = FluxaEasing.EASE_IN_OUT,
)

data class Transition(
    val property: String,
    val duration: FluxaDuration,
    val easing: FluxaEasing,
) : FluxaUtility {
    override fun instruction() = dev.fluxa.runtime.FluxaStyleInstruction(
        "transition",
        "$property|${duration.ms}|${easing.name.lowercase()}",
    )
}

data class AnimateOn(
    val variant: FluxaVariant,
    val duration: FluxaDuration = FluxaDuration.NORMAL,
    val easing: FluxaEasing = FluxaEasing.EASE_IN_OUT,
) : FluxaUtility {
    override fun instruction() = dev.fluxa.runtime.FluxaStyleInstruction(
        "animateOn",
        "${variant.name.lowercase()}|${duration.ms}|${easing.name.lowercase()}",
    )
}
