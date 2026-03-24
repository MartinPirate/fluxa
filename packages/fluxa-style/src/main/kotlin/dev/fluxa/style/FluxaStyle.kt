package dev.fluxa.style

import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.runtime.FluxaStyleInstruction
import dev.fluxa.runtime.FluxaStyleSpec

@DslMarker
annotation class FluxaStyleDsl

enum class FluxaAxisScale(val step: Int) {
    NONE(0),
    XS(1),
    SM(2),
    MD(4),
    LG(6),
    XL(8),
    XXL(12),
}

enum class FluxaBorderScale(val step: Int) {
    NONE(0),
    THIN(1),
    MD(2),
    LG(3),
}

enum class FluxaRadiusScale(val step: Int) {
    NONE(0),
    SM(4),
    MD(8),
    LG(16),
    XL(24),
    PILL(999),
}

enum class FluxaWeight(val value: Int) {
    REGULAR(400),
    MEDIUM(500),
    SEMIBOLD(600),
    BOLD(700),
}

enum class FluxaAlignment {
    START,
    CENTER,
    END,
    SPACE_BETWEEN,
}

enum class FluxaVariant {
    DEFAULT,
    PRESSED,
    DISABLED,
    SELECTED,
    EMPHASIS,
}

data class FluxaToken(
    val name: String,
    val value: String,
)

sealed interface FluxaUtility {
    fun instruction(): FluxaStyleInstruction
}

data class Padding(val scale: FluxaAxisScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("padding", scale.step.toString())
}

data class PaddingX(val scale: FluxaAxisScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("paddingX", scale.step.toString())
}

data class PaddingY(val scale: FluxaAxisScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("paddingY", scale.step.toString())
}

data class Gap(val scale: FluxaAxisScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("gap", scale.step.toString())
}

data class Background(val token: String) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("background", token)
}

data class Foreground(val token: String) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("foreground", token)
}

data class Radius(val scale: FluxaRadiusScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("radius", scale.step.toString())
}

data class Border(val token: String, val scale: FluxaBorderScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("border", "${token}|${scale.step}")
}

data class Shadow(val scale: FluxaAxisScale) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("shadow", scale.step.toString())
}

data class Typography(val token: String) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("typography", token)
}

data class FontWeight(val weight: FluxaWeight) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("fontWeight", weight.value.toString())
}

data class Opacity(val value: Float) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("opacity", value.toString())
}

data class Width(val value: String) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("width", value)
}

data class Height(val value: String) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("height", value)
}

data class AlignItems(val alignment: FluxaAlignment) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("alignItems", alignment.name.lowercase())
}

data class JustifyContent(val alignment: FluxaAlignment) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("justifyContent", alignment.name.lowercase())
}

data class AlignSelf(val alignment: FluxaAlignment) : FluxaUtility {
    override fun instruction(): FluxaStyleInstruction = FluxaStyleInstruction("alignSelf", alignment.name.lowercase())
}

data class FluxaStyle(
    val base: List<FluxaUtility> = emptyList(),
    val tokens: List<FluxaToken> = emptyList(),
    val variants: Map<FluxaVariant, List<FluxaUtility>> = emptyMap(),
    val responsive: Map<FluxaBreakpoint, List<FluxaUtility>> = emptyMap(),
)

fun styleOf(vararg utilities: FluxaUtility): FluxaStyle = FluxaStyle(base = utilities.toList())

fun style(block: FluxaStyleBuilder.() -> Unit): FluxaStyle = FluxaStyleBuilder().apply(block).build()

fun FluxaStyle.withTheme(theme: FluxaThemeTokens): FluxaStyle = copy(
    tokens = theme.allTokens() + tokens,
)

fun FluxaStyle.compile(): FluxaStyleSpec = FluxaStyleSpec(
    base = base.map { it.instruction() },
    tokens = tokens.associate { it.name to it.value },
    variants = variants.mapKeys { it.key.name.lowercase() }
        .mapValues { (_, utilities) -> utilities.map { it.instruction() } },
    responsive = responsive.mapValues { (_, utilities) -> utilities.map { it.instruction() } },
)

@FluxaStyleDsl
class FluxaStyleBuilder {
    private val base = mutableListOf<FluxaUtility>()
    private val tokens = mutableListOf<FluxaToken>()
    private val variants = linkedMapOf<FluxaVariant, MutableList<FluxaUtility>>()
    private val responsive = linkedMapOf<FluxaBreakpoint, MutableList<FluxaUtility>>()

    fun token(name: String, value: String) {
        tokens += FluxaToken(name, value)
    }

    fun theme(theme: FluxaThemeTokens) {
        tokens += theme.allTokens()
    }

    fun use(vararg utilities: FluxaUtility) {
        base += utilities
    }

    fun padding(scale: FluxaAxisScale) = use(Padding(scale))

    fun paddingX(scale: FluxaAxisScale) = use(PaddingX(scale))

    fun paddingY(scale: FluxaAxisScale) = use(PaddingY(scale))

    fun gap(scale: FluxaAxisScale) = use(Gap(scale))

    fun background(token: String) = use(Background(token))

    fun background(token: FluxaThemeColor) = background(token.name)

    fun foreground(token: String) = use(Foreground(token))

    fun foreground(token: FluxaThemeColor) = foreground(token.name)

    fun radius(scale: FluxaRadiusScale) = use(Radius(scale))

    fun border(token: String, scale: FluxaBorderScale = FluxaBorderScale.THIN) = use(Border(token, scale))

    fun border(token: FluxaThemeColor, scale: FluxaBorderScale = FluxaBorderScale.THIN) = border(token.name, scale)

    fun shadow(scale: FluxaAxisScale) = use(Shadow(scale))

    fun typography(token: String) = use(Typography(token))

    fun typography(token: FluxaThemeTypography) = typography(token.name)

    fun weight(weight: FluxaWeight) = use(FontWeight(weight))

    fun opacity(value: Float) = use(Opacity(value))

    fun width(value: String) = use(Width(value))

    fun height(value: String) = use(Height(value))

    fun alignItems(alignment: FluxaAlignment) = use(AlignItems(alignment))

    fun justifyContent(alignment: FluxaAlignment) = use(JustifyContent(alignment))

    fun alignSelf(alignment: FluxaAlignment) = use(AlignSelf(alignment))

    fun transition(
        property: String,
        duration: FluxaDuration = FluxaDuration.NORMAL,
        easing: FluxaEasing = FluxaEasing.EASE_IN_OUT,
    ) = use(Transition(property, duration, easing))

    fun animateOn(
        variant: FluxaVariant,
        duration: FluxaDuration = FluxaDuration.NORMAL,
        easing: FluxaEasing = FluxaEasing.EASE_IN_OUT,
    ) = use(AnimateOn(variant, duration, easing))

    fun gesture(type: FluxaGestureType, action: String = type.name.lowercase()) =
        use(GestureHandler(type, action))

    fun clickable(action: String = "click") = use(Clickable(action))

    fun scrollable(direction: String = "vertical") = use(Scrollable(direction))

    fun variant(name: FluxaVariant, block: FluxaVariantBuilder.() -> Unit) {
        val utilities = FluxaVariantBuilder().apply(block).build()
        variants.getOrPut(name) { mutableListOf() } += utilities
    }

    fun responsive(breakpoint: FluxaBreakpoint, block: FluxaVariantBuilder.() -> Unit) {
        val utilities = FluxaVariantBuilder().apply(block).build()
        responsive.getOrPut(breakpoint) { mutableListOf() } += utilities
    }

    fun build(): FluxaStyle = FluxaStyle(
        base = base.toList(),
        tokens = tokens.toList(),
        variants = variants.mapValues { (_, value) -> value.toList() },
        responsive = responsive.mapValues { (_, value) -> value.toList() },
    )
}

@FluxaStyleDsl
class FluxaVariantBuilder {
    private val utilities = mutableListOf<FluxaUtility>()

    fun use(vararg utilities: FluxaUtility) {
        this.utilities += utilities
    }

    fun padding(scale: FluxaAxisScale) = use(Padding(scale))

    fun paddingX(scale: FluxaAxisScale) = use(PaddingX(scale))

    fun paddingY(scale: FluxaAxisScale) = use(PaddingY(scale))

    fun gap(scale: FluxaAxisScale) = use(Gap(scale))

    fun background(token: String) = use(Background(token))

    fun background(token: FluxaThemeColor) = background(token.name)

    fun foreground(token: String) = use(Foreground(token))

    fun foreground(token: FluxaThemeColor) = foreground(token.name)

    fun radius(scale: FluxaRadiusScale) = use(Radius(scale))

    fun border(token: String, scale: FluxaBorderScale = FluxaBorderScale.THIN) = use(Border(token, scale))

    fun border(token: FluxaThemeColor, scale: FluxaBorderScale = FluxaBorderScale.THIN) = border(token.name, scale)

    fun shadow(scale: FluxaAxisScale) = use(Shadow(scale))

    fun typography(token: String) = use(Typography(token))

    fun typography(token: FluxaThemeTypography) = typography(token.name)

    fun weight(weight: FluxaWeight) = use(FontWeight(weight))

    fun opacity(value: Float) = use(Opacity(value))

    fun width(value: String) = use(Width(value))

    fun height(value: String) = use(Height(value))

    fun alignItems(alignment: FluxaAlignment) = use(AlignItems(alignment))

    fun justifyContent(alignment: FluxaAlignment) = use(JustifyContent(alignment))

    fun alignSelf(alignment: FluxaAlignment) = use(AlignSelf(alignment))

    fun build(): List<FluxaUtility> = utilities.toList()
}
