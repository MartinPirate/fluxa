package dev.fluxa.style

data class FluxaThemeColor(
    val name: String,
    val value: String,
)

data class FluxaThemeTypography(
    val name: String,
    val value: String,
)

data class FluxaColorScheme(
    val page: FluxaThemeColor,
    val textPrimary: FluxaThemeColor,
    val textInverse: FluxaThemeColor,
    val panel: FluxaThemeColor,
    val panelAccent: FluxaThemeColor,
    val panelBorder: FluxaThemeColor,
    val pill: FluxaThemeColor,
    val pillText: FluxaThemeColor,
    val success: FluxaThemeColor,
    val successText: FluxaThemeColor,
    val spotlight: FluxaThemeColor,
    val spotlightText: FluxaThemeColor,
    val warning: FluxaThemeColor,
    val warningText: FluxaThemeColor,
)

data class FluxaTypeScale(
    val hero: FluxaThemeTypography,
    val title: FluxaThemeTypography,
    val label: FluxaThemeTypography,
)

data class FluxaThemeTokens(
    val name: String,
    val colors: FluxaColorScheme,
    val typography: FluxaTypeScale,
) {
    fun allTokens(): List<FluxaToken> = buildList {
        addAll(
            listOf(
                colors.page,
                colors.textPrimary,
                colors.textInverse,
                colors.panel,
                colors.panelAccent,
                colors.panelBorder,
                colors.pill,
                colors.pillText,
                colors.success,
                colors.successText,
                colors.spotlight,
                colors.spotlightText,
                colors.warning,
                colors.warningText,
            ).map { FluxaToken(it.name, it.value) },
        )
        addAll(
            listOf(
                typography.hero,
                typography.title,
                typography.label,
            ).map { FluxaToken(it.name, it.value) },
        )
    }
}

object FluxaThemes {
    val Aurora = FluxaThemeTokens(
        name = "Aurora",
        colors = FluxaColorScheme(
            page = FluxaThemeColor("color.page", "#F4F7FB"),
            textPrimary = FluxaThemeColor("color.textPrimary", "#0F1720"),
            textInverse = FluxaThemeColor("color.textInverse", "#F8FAFC"),
            panel = FluxaThemeColor("color.panel", "#E8EEF9"),
            panelAccent = FluxaThemeColor("color.panelAccent", "#D5E4FF"),
            panelBorder = FluxaThemeColor("color.panelBorder", "#C2D6F6"),
            pill = FluxaThemeColor("color.pill", "#DCE8FF"),
            pillText = FluxaThemeColor("color.pillText", "#14315C"),
            success = FluxaThemeColor("color.success", "#B7F5C8"),
            successText = FluxaThemeColor("color.successText", "#0C3B1D"),
            spotlight = FluxaThemeColor("color.spotlight", "#111827"),
            spotlightText = FluxaThemeColor("color.spotlightText", "#F8FAFC"),
            warning = FluxaThemeColor("color.warning", "#FFF1D6"),
            warningText = FluxaThemeColor("color.warningText", "#2B1C00"),
        ),
        typography = FluxaTypeScale(
            hero = FluxaThemeTypography("type.hero", "hero"),
            title = FluxaThemeTypography("type.title", "title-lg"),
            label = FluxaThemeTypography("type.label", "label-md"),
        ),
    )
}
