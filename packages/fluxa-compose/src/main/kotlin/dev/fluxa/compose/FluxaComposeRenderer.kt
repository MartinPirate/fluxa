package dev.fluxa.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.fluxa.ui.FluxaSemantics
import dev.fluxa.runtime.FluxaBreakpoint
import dev.fluxa.style.FluxaDuration
import dev.fluxa.style.FluxaEasing
import dev.fluxa.runtime.FluxaStyleInstruction
import dev.fluxa.runtime.FluxaStyleSpec
import dev.fluxa.style.FluxaStyle
import dev.fluxa.style.FluxaThemeTokens
import dev.fluxa.style.FluxaThemes
import dev.fluxa.style.FluxaVariant
import dev.fluxa.style.compile
import dev.fluxa.style.withTheme
import dev.fluxa.ui.FluxaNode

data class FluxaRenderContext(
    val breakpoint: FluxaBreakpoint = FluxaBreakpoint.COMPACT,
    val activeVariants: Set<FluxaVariant> = emptySet(),
)

val LocalFluxaTheme = staticCompositionLocalOf<FluxaThemeTokens> { FluxaThemes.Aurora }

@Composable
fun FluxaTheme(
    theme: FluxaThemeTokens = FluxaThemes.Aurora,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalFluxaTheme provides theme) {
        MaterialTheme(content = content)
    }
}

@Composable
fun RenderFluxaNode(
    node: FluxaNode,
    modifier: Modifier = Modifier,
    context: FluxaRenderContext = FluxaRenderContext(),
    inheritedForeground: Color? = null,
    inheritedTextStyle: TextStyle? = null,
) {
    val resolved = node.style.withTheme(LocalFluxaTheme.current).resolve(
        context = context.copy(activeVariants = context.activeVariants + node.activeVariants),
    )

    val baseModifier = modifier.then(resolved.modifier).thenSemantics(node.semantics)

    when (node.type) {
        "Screen" -> RenderScreen(node, baseModifier, context, resolved, inheritedForeground, inheritedTextStyle)
        "Column" -> RenderColumn(node, baseModifier, context, resolved, inheritedForeground, inheritedTextStyle)
        "Row" -> RenderRow(node, baseModifier, context, resolved, inheritedForeground, inheritedTextStyle)
        "Stack" -> RenderStack(node, baseModifier, context, resolved, inheritedForeground, inheritedTextStyle)
        "Text" -> RenderText(node, baseModifier, resolved, inheritedForeground, inheritedTextStyle)
        "TextField" -> RenderTextField(node, baseModifier, resolved)
        "Toggle" -> RenderToggle(node, baseModifier, resolved, inheritedForeground, inheritedTextStyle)
        "Checkbox" -> RenderCheckbox(node, baseModifier, resolved, inheritedForeground, inheritedTextStyle)
        "Button" -> RenderButton(node, baseModifier, resolved)
        "Divider" -> RenderDivider(node, baseModifier, resolved)
        "Spacer" -> RenderSpacer(baseModifier, resolved)
        "Image" -> RenderImage(node, baseModifier, resolved)
        "LazyColumn" -> RenderLazyColumn(node, baseModifier, context, resolved, inheritedForeground, inheritedTextStyle)
        "LazyRow" -> RenderLazyRow(node, baseModifier, context, resolved, inheritedForeground, inheritedTextStyle)
        else -> RenderFallback(node, modifier)
    }
}

@Composable
private fun RenderScreen(
    node: FluxaNode,
    modifier: Modifier,
    context: FluxaRenderContext,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val responsiveContext = context.copy(breakpoint = widthBreakpoint(maxWidth.value))
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                node.children.forEach { child ->
                    RenderFluxaNode(
                        node = child,
                        context = responsiveContext,
                        inheritedForeground = resolved.foreground ?: inheritedForeground,
                        inheritedTextStyle = resolved.textStyle ?: inheritedTextStyle,
                    )
                }
            }
        }
    }
}

@Composable
private fun RenderColumn(
    node: FluxaNode,
    modifier: Modifier,
    context: FluxaRenderContext,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    Column(
        modifier = modifier,
        verticalArrangement = resolved.verticalArrangement,
        horizontalAlignment = resolved.horizontalAlignment,
    ) {
        node.children.forEach { child ->
            val childResolved = child.style.withTheme(LocalFluxaTheme.current).resolve(
                context.copy(activeVariants = context.activeVariants + child.activeVariants),
            )
            RenderFluxaNode(
                node = child,
                modifier = childColumnModifier(childResolved),
                context = context,
                inheritedForeground = resolved.foreground ?: inheritedForeground,
                inheritedTextStyle = resolved.textStyle ?: inheritedTextStyle,
            )
        }
    }
}

@Composable
private fun RenderRow(
    node: FluxaNode,
    modifier: Modifier,
    context: FluxaRenderContext,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = resolved.horizontalArrangement,
        verticalAlignment = resolved.verticalAlignment,
    ) {
        node.children.forEach { child ->
            val childResolved = child.style.withTheme(LocalFluxaTheme.current).resolve(
                context.copy(activeVariants = context.activeVariants + child.activeVariants),
            )
            RenderFluxaNode(
                node = child,
                modifier = childRowModifier(childResolved),
                context = context,
                inheritedForeground = resolved.foreground ?: inheritedForeground,
                inheritedTextStyle = resolved.textStyle ?: inheritedTextStyle,
            )
        }
    }
}

@Composable
private fun RenderStack(
    node: FluxaNode,
    modifier: Modifier,
    context: FluxaRenderContext,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    Box(
        modifier = modifier,
        contentAlignment = resolved.contentAlignment,
    ) {
        node.children.forEach { child ->
            val childResolved = child.style.withTheme(LocalFluxaTheme.current).resolve(
                context.copy(activeVariants = context.activeVariants + child.activeVariants),
            )
            RenderFluxaNode(
                node = child,
                modifier = childStackModifier(childResolved),
                context = context,
                inheritedForeground = resolved.foreground ?: inheritedForeground,
                inheritedTextStyle = resolved.textStyle ?: inheritedTextStyle,
            )
        }
    }
}

@Composable
private fun RenderText(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    Text(
        text = node.text.orEmpty(),
        modifier = modifier,
        color = resolved.foreground ?: inheritedForeground ?: MaterialTheme.colorScheme.onSurface,
        style = resolved.textStyle ?: inheritedTextStyle ?: MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun RenderFallback(node: FluxaNode, modifier: Modifier) {
    Text(
        text = "Unsupported node: ${node.type}",
        modifier = modifier,
        color = MaterialTheme.colorScheme.error,
    )
}

private data class ResolvedStyle(
    val modifier: Modifier = Modifier,
    val foreground: Color? = null,
    val textStyle: TextStyle? = null,
    val horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    val verticalAlignment: Alignment.Vertical = Alignment.Top,
    val contentAlignment: Alignment = Alignment.TopStart,
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    val selfAlignment: String? = null,
    val transitions: List<ResolvedTransition> = emptyList(),
)

data class ResolvedTransition(
    val property: String,
    val durationMs: Int,
    val easing: String,
)

private fun FluxaStyle.resolve(context: FluxaRenderContext): ResolvedStyle = compile().resolve(context)

private fun FluxaStyleSpec.resolve(context: FluxaRenderContext): ResolvedStyle {
    val tokenMap = tokens
    val instructions = buildList {
        addAll(base)
        addAll(responsive[context.breakpoint].orEmpty())
        context.activeVariants.forEach { variant ->
            addAll(variants[variant.name.lowercase()].orEmpty())
        }
    }

    var modifier: Modifier = Modifier
    var foreground: Color? = null
    var typography: TextStyle? = null
    var fontWeight: FontWeight? = null
    var horizontalAlignment = Alignment.Start
    var verticalAlignment = Alignment.Top
    var contentAlignment = Alignment.TopStart
    var horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
    var verticalArrangement: Arrangement.Vertical = Arrangement.Top
    var selfAlignment: String? = null
    val transitions = mutableListOf<ResolvedTransition>()

    instructions.forEach { instruction ->
        when (instruction.name) {
            "padding" -> modifier = modifier.padding(instruction.value.toIntOrNull()?.dp ?: 0.dp)
            "paddingX" -> modifier = modifier.padding(horizontal = instruction.value.toIntOrNull()?.dp ?: 0.dp)
            "paddingY" -> modifier = modifier.padding(vertical = instruction.value.toIntOrNull()?.dp ?: 0.dp)
            "gap" -> {
                val space = (instruction.value.toIntOrNull() ?: 0).dp
                horizontalArrangement = Arrangement.spacedBy(space)
                verticalArrangement = Arrangement.spacedBy(space)
            }
            "background" -> {
                val color = tokenMap.resolveColor(instruction)
                val radius = instructions.lastOrNull { it.name == "radius" }?.value?.toIntOrNull() ?: 0
                modifier = if (radius > 0) {
                    modifier.clip(RoundedCornerShape(radius.dp)).background(color)
                } else {
                    modifier.background(color)
                }
            }
            "radius" -> {
                if (instructions.none { it.name == "background" }) {
                    val radius = instruction.value.toIntOrNull() ?: 0
                    modifier = modifier.clip(RoundedCornerShape(radius.dp))
                }
            }
            "opacity" -> modifier = modifier.alpha(instruction.value.toFloatOrNull() ?: 1f)
            "foreground" -> foreground = tokenMap.resolveColor(instruction)
            "typography" -> typography = tokenMap.resolveTypography(instruction)
            "fontWeight" -> fontWeight = instruction.value.toIntOrNull()?.toFontWeight()
            "width" -> modifier = modifier.thenWidth(instruction.value)
            "height" -> modifier = modifier.thenHeight(instruction.value)
            "alignItems" -> {
                horizontalAlignment = instruction.value.toHorizontalAlignment()
                verticalAlignment = instruction.value.toVerticalAlignment()
                contentAlignment = instruction.value.toContentAlignment()
            }
            "justifyContent" -> {
                horizontalArrangement = instruction.value.toHorizontalArrangement()
                verticalArrangement = instruction.value.toVerticalArrangement()
            }
            "alignSelf" -> selfAlignment = instruction.value
            "border" -> {
                val (token, width) = instruction.value.split("|").let { parts ->
                    parts.first() to (parts.getOrNull(1)?.toIntOrNull() ?: 1)
                }
                val radius = instructions.lastOrNull { it.name == "radius" }?.value?.toIntOrNull() ?: 0
                val shape = radius.toShape()
                modifier = modifier.border(width.dp, tokenMap.resolveColor(token), shape)
            }
            "shadow" -> {
                val radius = instructions.lastOrNull { it.name == "radius" }?.value?.toIntOrNull() ?: 0
                modifier = modifier.shadow(instruction.value.toIntOrNull()?.dp ?: 0.dp, radius.toShape())
            }
            "transition" -> {
                val parts = instruction.value.split("|")
                transitions += ResolvedTransition(
                    property = parts[0],
                    durationMs = parts.getOrNull(1)?.toIntOrNull() ?: 200,
                    easing = parts.getOrNull(2) ?: "ease_in_out",
                )
            }
            "animateOn" -> {
                val parts = instruction.value.split("|")
                transitions += ResolvedTransition(
                    property = parts[0],
                    durationMs = parts.getOrNull(1)?.toIntOrNull() ?: 200,
                    easing = parts.getOrNull(2) ?: "ease_in_out",
                )
            }
        }
    }

    val textStyle = typography?.let { baseStyle ->
        fontWeight?.let { baseStyle.copy(fontWeight = it) } ?: baseStyle
    } ?: fontWeight?.let { TextStyle(fontWeight = it) }

    return ResolvedStyle(
        modifier = modifier,
        foreground = foreground,
        textStyle = textStyle,
        horizontalAlignment = horizontalAlignment,
        verticalAlignment = verticalAlignment,
        contentAlignment = contentAlignment,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        selfAlignment = selfAlignment,
        transitions = transitions,
    )
}

private fun Map<String, String>.resolveColor(instruction: FluxaStyleInstruction): Color {
    val raw = this[instruction.value] ?: instruction.value
    return raw.toColor()
}

private fun Map<String, String>.resolveTypography(instruction: FluxaStyleInstruction): TextStyle? {
    val raw = this[instruction.value] ?: instruction.value
    return when (raw) {
        "hero" -> TextStyle(fontSize = 42.sp, lineHeight = 48.sp, fontWeight = FontWeight.Bold)
        "title-lg" -> TextStyle(fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.SemiBold)
        "body-md" -> TextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Normal)
        "label-md" -> TextStyle(fontSize = 14.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium)
        "caption-sm" -> TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal)
        else -> null
    }
}

private fun String.toColor(): Color = runCatching {
    Color(android.graphics.Color.parseColor(this))
}.getOrElse {
    Color.Unspecified
}

private fun Map<String, String>.resolveColor(token: String): Color {
    val raw = this[token] ?: token
    return raw.toColor()
}

private fun widthBreakpoint(widthDp: Float): FluxaBreakpoint = when {
    widthDp >= 840f -> FluxaBreakpoint.EXPANDED
    widthDp >= 600f -> FluxaBreakpoint.MEDIUM
    else -> FluxaBreakpoint.COMPACT
}

private fun Int.toFontWeight(): FontWeight = when (this) {
    500 -> FontWeight.Medium
    600 -> FontWeight.SemiBold
    700 -> FontWeight.Bold
    else -> FontWeight.Normal
}

private fun Modifier.thenWidth(value: String): Modifier = when (value) {
    "full" -> fillMaxWidth()
    else -> this
}

private fun Modifier.thenHeight(value: String): Modifier = when {
    value == "full" -> fillMaxHeight()
    value.toIntOrNull() != null -> height(value.toInt().dp)
    else -> this
}

private fun String.toHorizontalAlignment(): Alignment.Horizontal = when (this) {
    "center" -> Alignment.CenterHorizontally
    "end" -> Alignment.End
    else -> Alignment.Start
}

private fun String.toVerticalAlignment(): Alignment.Vertical = when (this) {
    "center" -> Alignment.CenterVertically
    "end" -> Alignment.Bottom
    else -> Alignment.Top
}

private fun String.toContentAlignment(): Alignment = when (this) {
    "center" -> Alignment.Center
    "end" -> Alignment.BottomEnd
    else -> Alignment.TopStart
}

private fun String.toHorizontalArrangement(): Arrangement.Horizontal = when (this) {
    "center" -> Arrangement.Center
    "end" -> Arrangement.End
    "space_between" -> Arrangement.SpaceBetween
    else -> Arrangement.Start
}

private fun String.toVerticalArrangement(): Arrangement.Vertical = when (this) {
    "center" -> Arrangement.Center
    "end" -> Arrangement.Bottom
    "space_between" -> Arrangement.SpaceBetween
    else -> Arrangement.Top
}

private fun Int.toShape(): Shape = if (this > 0) RoundedCornerShape(this.dp) else RectangleShape

private fun ColumnScope.childColumnModifier(child: ResolvedStyle): Modifier = when (child.selfAlignment) {
    "center" -> Modifier.align(Alignment.CenterHorizontally)
    "end" -> Modifier.align(Alignment.End)
    else -> Modifier
}

private fun RowScope.childRowModifier(child: ResolvedStyle): Modifier = when (child.selfAlignment) {
    "center" -> Modifier.align(Alignment.CenterVertically)
    "end" -> Modifier.align(Alignment.Bottom)
    else -> Modifier
}

private fun BoxScope.childStackModifier(child: ResolvedStyle): Modifier = when (child.selfAlignment) {
    "center" -> Modifier.align(Alignment.Center)
    "end" -> Modifier.align(Alignment.BottomEnd)
    else -> Modifier.align(Alignment.TopStart)
}

@Composable
private fun RenderTextField(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
) {
    val placeholder = node.meta["placeholder"].orEmpty()
    val enabled = node.meta["enabled"] != "false"
    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        modifier = modifier,
        label = node.text?.takeIf { it.isNotBlank() }?.let { { Text(it) } },
        placeholder = placeholder.takeIf { it.isNotBlank() }?.let { { Text(it) } },
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
private fun RenderToggle(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    val enabled = node.meta["enabled"] != "false"
    var checked by remember { mutableStateOf(node.meta["checked"] == "true") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        node.text?.takeIf { it.isNotBlank() }?.let {
            Text(
                text = it,
                color = resolved.foreground ?: inheritedForeground ?: MaterialTheme.colorScheme.onSurface,
                style = resolved.textStyle ?: inheritedTextStyle ?: MaterialTheme.typography.bodyLarge,
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            enabled = enabled,
        )
    }
}

@Composable
private fun RenderCheckbox(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    val enabled = node.meta["enabled"] != "false"
    var checked by remember { mutableStateOf(node.meta["checked"] == "true") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            enabled = enabled,
        )
        node.text?.takeIf { it.isNotBlank() }?.let {
            Text(
                text = it,
                color = resolved.foreground ?: inheritedForeground ?: MaterialTheme.colorScheme.onSurface,
                style = resolved.textStyle ?: inheritedTextStyle ?: MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun RenderButton(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
) {
    val enabled = node.meta["enabled"] != "false"

    Button(
        onClick = {},
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = resolved.foreground?.let { Color.Unspecified } ?: MaterialTheme.colorScheme.primary,
        ),
    ) {
        Text(text = node.text.orEmpty())
    }
}

@Composable
private fun RenderDivider(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
) {
    HorizontalDivider(
        modifier = modifier,
        color = resolved.foreground ?: MaterialTheme.colorScheme.outline,
    )
}

@Composable
private fun RenderSpacer(
    modifier: Modifier,
    resolved: ResolvedStyle,
) {
    Spacer(modifier = modifier)
}

@Composable
private fun RenderImage(
    node: FluxaNode,
    modifier: Modifier,
    resolved: ResolvedStyle,
) {
    // Image rendering placeholder — will integrate with image loading in a future pass
    Box(
        modifier = modifier.background(
            resolved.foreground ?: Color.LightGray,
            RoundedCornerShape(8.dp),
        ),
        contentAlignment = Alignment.Center,
    ) {
        node.text?.let {
            Text(text = it, color = Color.White, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun RenderLazyColumn(
    node: FluxaNode,
    modifier: Modifier,
    context: FluxaRenderContext,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    androidx.compose.foundation.lazy.LazyColumn(
        modifier = modifier,
        verticalArrangement = resolved.verticalArrangement,
        horizontalAlignment = resolved.horizontalAlignment,
    ) {
        items(node.children.size) { index ->
            val child = node.children[index]
            RenderFluxaNode(
                node = child,
                context = context,
                inheritedForeground = resolved.foreground ?: inheritedForeground,
                inheritedTextStyle = resolved.textStyle ?: inheritedTextStyle,
            )
        }
    }
}

@Composable
private fun RenderLazyRow(
    node: FluxaNode,
    modifier: Modifier,
    context: FluxaRenderContext,
    resolved: ResolvedStyle,
    inheritedForeground: Color?,
    inheritedTextStyle: TextStyle?,
) {
    androidx.compose.foundation.lazy.LazyRow(
        modifier = modifier,
        horizontalArrangement = resolved.horizontalArrangement,
        verticalAlignment = resolved.verticalAlignment,
    ) {
        items(node.children.size) { index ->
            val child = node.children[index]
            RenderFluxaNode(
                node = child,
                context = context,
                inheritedForeground = resolved.foreground ?: inheritedForeground,
                inheritedTextStyle = resolved.textStyle ?: inheritedTextStyle,
            )
        }
    }
}

private fun Modifier.thenSemantics(semantics: FluxaSemantics?): Modifier {
    if (semantics == null) return this
    return this.semantics {
        semantics.contentDescription?.let { contentDescription = it }
        if (semantics.heading) heading()
    }
}
