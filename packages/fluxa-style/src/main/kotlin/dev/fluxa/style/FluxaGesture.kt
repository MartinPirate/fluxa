package dev.fluxa.style

import dev.fluxa.runtime.FluxaStyleInstruction

enum class FluxaGestureType {
    TAP,
    LONG_PRESS,
    DOUBLE_TAP,
    SWIPE_LEFT,
    SWIPE_RIGHT,
    SWIPE_UP,
    SWIPE_DOWN,
}

data class GestureHandler(
    val gesture: FluxaGestureType,
    val action: String,
) : FluxaUtility {
    override fun instruction() = FluxaStyleInstruction(
        "gesture",
        "${gesture.name.lowercase()}|$action",
    )
}

data class Clickable(val action: String = "click") : FluxaUtility {
    override fun instruction() = FluxaStyleInstruction("clickable", action)
}

data class Scrollable(val direction: String = "vertical") : FluxaUtility {
    override fun instruction() = FluxaStyleInstruction("scrollable", direction)
}
