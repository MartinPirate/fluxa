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
    val textSecondary: FluxaThemeColor,
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
    val error: FluxaThemeColor,
    val errorText: FluxaThemeColor,
    val divider: FluxaThemeColor,
    val inputBackground: FluxaThemeColor,
    val inputBorder: FluxaThemeColor,
)

data class FluxaTypeScale(
    val hero: FluxaThemeTypography,
    val title: FluxaThemeTypography,
    val body: FluxaThemeTypography,
    val label: FluxaThemeTypography,
    val caption: FluxaThemeTypography,
)

enum class FluxaThemeMode {
    LIGHT,
    DARK,
}

data class FluxaThemeTokens(
    val name: String,
    val mode: FluxaThemeMode,
    val colors: FluxaColorScheme,
    val typography: FluxaTypeScale,
) {
    fun allTokens(): List<FluxaToken> = buildList {
        addAll(
            listOf(
                colors.page,
                colors.textPrimary,
                colors.textSecondary,
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
                colors.error,
                colors.errorText,
                colors.divider,
                colors.inputBackground,
                colors.inputBorder,
            ).map { FluxaToken(it.name, it.value) },
        )
        addAll(
            listOf(
                typography.hero,
                typography.title,
                typography.body,
                typography.label,
                typography.caption,
            ).map { FluxaToken(it.name, it.value) },
        )
    }
}

private val auroraTypography = FluxaTypeScale(
    hero = FluxaThemeTypography("type.hero", "hero"),
    title = FluxaThemeTypography("type.title", "title-lg"),
    body = FluxaThemeTypography("type.body", "body-md"),
    label = FluxaThemeTypography("type.label", "label-md"),
    caption = FluxaThemeTypography("type.caption", "caption-sm"),
)

object FluxaThemes {
    val Aurora = FluxaThemeTokens(
        name = "Aurora",
        mode = FluxaThemeMode.LIGHT,
        colors = FluxaColorScheme(
            page = FluxaThemeColor("color.page", "#F4F7FB"),
            textPrimary = FluxaThemeColor("color.textPrimary", "#0F1720"),
            textSecondary = FluxaThemeColor("color.textSecondary", "#5A6778"),
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
            error = FluxaThemeColor("color.error", "#FFE0E0"),
            errorText = FluxaThemeColor("color.errorText", "#7A1010"),
            divider = FluxaThemeColor("color.divider", "#D0D9E6"),
            inputBackground = FluxaThemeColor("color.inputBackground", "#FFFFFF"),
            inputBorder = FluxaThemeColor("color.inputBorder", "#B8C5D6"),
        ),
        typography = auroraTypography,
    )

    val AuroraDark = FluxaThemeTokens(
        name = "Aurora Dark",
        mode = FluxaThemeMode.DARK,
        colors = FluxaColorScheme(
            page = FluxaThemeColor("color.page", "#0F1720"),
            textPrimary = FluxaThemeColor("color.textPrimary", "#E8EEF4"),
            textSecondary = FluxaThemeColor("color.textSecondary", "#8A96A6"),
            textInverse = FluxaThemeColor("color.textInverse", "#0F1720"),
            panel = FluxaThemeColor("color.panel", "#1A2433"),
            panelAccent = FluxaThemeColor("color.panelAccent", "#243044"),
            panelBorder = FluxaThemeColor("color.panelBorder", "#2E3D50"),
            pill = FluxaThemeColor("color.pill", "#1E3050"),
            pillText = FluxaThemeColor("color.pillText", "#A8C8F0"),
            success = FluxaThemeColor("color.success", "#133D20"),
            successText = FluxaThemeColor("color.successText", "#A2EDBA"),
            spotlight = FluxaThemeColor("color.spotlight", "#F4F7FB"),
            spotlightText = FluxaThemeColor("color.spotlightText", "#0F1720"),
            warning = FluxaThemeColor("color.warning", "#3D2E00"),
            warningText = FluxaThemeColor("color.warningText", "#FFDFA0"),
            error = FluxaThemeColor("color.error", "#3D1010"),
            errorText = FluxaThemeColor("color.errorText", "#FFA0A0"),
            divider = FluxaThemeColor("color.divider", "#2A3545"),
            inputBackground = FluxaThemeColor("color.inputBackground", "#162030"),
            inputBorder = FluxaThemeColor("color.inputBorder", "#3A4A5E"),
        ),
        typography = auroraTypography,
    )
}
