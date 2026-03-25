import { CodeBlock } from "@/components/CodeBlock";
import { FeatureGrid } from "@/components/FeatureGrid";
import { ModuleMap } from "@/components/ModuleMap";
import { AnimateIn } from "@/components/AnimateIn";
import { CountUp } from "@/components/CountUp";
import { GradientOrbs } from "@/components/GradientOrbs";

const heroCode = `fun homeScreen(theme: FluxaThemeTokens) = screen(
    HeroPanel(
        title = "My App",
        subtitle = "Built with Fluxa",
        theme = theme,
    ),
    PillRow(
        labels = listOf("Fast", "Typed", "Themeable"),
        theme = theme,
    ),
    FeatureCard(
        title = "Welcome",
        body = "Start building.",
        theme = theme,
    ).onClick { navigate(Routes.Detail) },
)`;

const styleCode = `val cardStyle = style {
    background(theme.colors.panel)
    padding(FluxaAxisScale.LG)
    radius(FluxaRadiusScale.LG)
    border(theme.colors.panelBorder)
    shadow(FluxaAxisScale.SM)

    variant(FluxaVariant.PRESSED) {
        background(theme.colors.panelAccent)
        opacity(0.92f)
    }

    responsive(FluxaBreakpoint.EXPANDED) {
        padding(FluxaAxisScale.XL)
    }
}`;

const storeCode = `val store = storeOf(
    initial = NoteState(),
    middleware = listOf(FluxaLogMiddleware("Notes")),
) { state, action ->
    when (action) {
        is Action.Add -> state.copy(
            notes = state.notes + action.note
        )
        is Action.Delete -> state.copy(
            notes = state.notes.filter { it.id != action.id }
        )
        is Action.Search -> state.copy(
            query = action.query
        )
    }
}`;

const navCode = `// Define typed routes
object Home : SimpleRoute("home")
data class NoteDetail(val id: String) : FluxaRoute("note/\$id")

// One-line router with animated transitions
FluxaRouter(backStack = backStack) { route ->
    when (route) {
        is Home -> homeScreen(state, theme)
        is NoteDetail -> detailScreen(route.id, theme)
    }
}`;

const cliCode = `$ fluxa new my-app --package com.example.app

Creating Fluxa project: my-app
  created settings.gradle.kts
  created app/build.gradle.kts
  created app/src/main/.../MainActivity.kt
  created app/src/main/.../ui/HomeScreen.kt
  created app/src/main/.../ui/theme/AppTheme.kt

Done! cd my-app && ./gradlew installDebug`;

const comparisonBefore = `// Raw Compose: 45 lines for one card
@Composable
fun NoteCard(note: Note) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFC2D6F6),
                RoundedCornerShape(16.dp))
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8EEF9)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement
                    .SpaceBetween,
                verticalAlignment = Alignment
                    .CenterVertically
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography
                        .titleMedium
                )
                // ... badge, body, etc
            }
        }
    }
}`;

const comparisonAfter = `// Fluxa: 12 lines for the same card
fun noteCard(note: Note, theme: FluxaThemeTokens) =
    SectionCard(
        title = note.title,
        theme = theme,
        headerTrailing = StatusBadge(
            label = note.category.label,
            theme = theme,
        ),
        children = arrayOf(
            text(note.body),
        ),
    ).onClick { navigate(note.id) }`;

export default function Home() {
  return (
    <main className="min-h-screen">
      {/* Nav */}
      <nav className="fixed top-0 left-0 right-0 z-50 backdrop-blur-xl bg-[var(--aurora-page)]/80 border-b border-[var(--aurora-panel-border)]">
        <div className="max-w-6xl mx-auto px-6 h-16 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 rounded-lg bg-[var(--aurora-spotlight)] flex items-center justify-center">
              <span className="text-white font-bold text-sm">F</span>
            </div>
            <span className="font-bold text-lg tracking-tight">Fluxa</span>
          </div>
          <div className="hidden md:flex items-center gap-8 text-sm text-[var(--aurora-text-secondary)]">
            <a href="#features" className="hover:text-[var(--aurora-text)] transition-colors">Features</a>
            <a href="#code" className="hover:text-[var(--aurora-text)] transition-colors">Code</a>
            <a href="#modules" className="hover:text-[var(--aurora-text)] transition-colors">Modules</a>
            <a
              href="https://github.com/MartinPirate/fluxa"
              target="_blank"
              rel="noopener noreferrer"
              className="px-4 py-2 rounded-lg bg-[var(--aurora-spotlight)] text-white text-sm font-medium hover:opacity-90 transition-opacity"
            >
              GitHub
            </a>
          </div>
        </div>
      </nav>

      {/* Hero */}
      <section className="relative pt-40 pb-24 px-6 overflow-hidden">
        <GradientOrbs />

        <div className="max-w-4xl mx-auto text-center relative z-10">
          <AnimateIn delay={100} direction="up" duration={800}>
            <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-[var(--aurora-pill)] text-[var(--aurora-pill-text)] text-sm font-medium mb-8">
              <span className="w-2 h-2 rounded-full bg-[var(--aurora-success)] animate-pulse" />
              v0.1.0-dev — 13 modules, 124 tests, all phases complete
            </div>
          </AnimateIn>

          <AnimateIn delay={250} direction="up" duration={900}>
            <h1 className="text-5xl md:text-7xl font-extrabold tracking-tight leading-[1.1] mb-6">
              Android UI that feels{" "}
              <span className="hero-gradient-text bg-gradient-to-r from-[#14315C] via-[#2563eb] to-[#7c3aed] bg-clip-text text-transparent">
                stricter, faster,
              </span>
              <br />
              and more{" "}
              <span className="hero-gradient-text bg-gradient-to-r from-[#7c3aed] via-[#2563eb] to-[#0C3B1D] bg-clip-text text-transparent">
                visual
              </span>
            </h1>
          </AnimateIn>

          <AnimateIn delay={450} direction="up" duration={800}>
            <p className="text-lg md:text-xl text-[var(--aurora-text-secondary)] max-w-2xl mx-auto mb-10 leading-relaxed">
              Fluxa is an opinionated framework layer on top of Jetpack Compose.
              Utility-first styling, typed navigation, unidirectional state, and a
              CLI that scaffolds real projects — not boilerplate.
            </p>
          </AnimateIn>

          <AnimateIn delay={600} direction="up" duration={800}>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <a
                href="https://github.com/MartinPirate/fluxa"
                target="_blank"
                rel="noopener noreferrer"
                className="cta-glow px-8 py-3.5 rounded-xl bg-[var(--aurora-spotlight)] text-white font-semibold hover:scale-[1.03] active:scale-[0.98] transition-transform shadow-lg shadow-[var(--aurora-spotlight)]/20"
              >
                Get Started
              </a>
              <a
                href="#code"
                className="px-8 py-3.5 rounded-xl bg-white border border-[var(--aurora-panel-border)] text-[var(--aurora-text)] font-semibold hover:bg-[var(--aurora-panel)] hover:scale-[1.02] active:scale-[0.98] transition-all"
              >
                See the Code
              </a>
            </div>
          </AnimateIn>
        </div>

        {/* Hero code preview */}
        <AnimateIn delay={800} direction="up" duration={1000}>
          <div className="max-w-2xl mx-auto mt-16 relative z-10">
            <CodeBlock code={heroCode} filename="HomeScreen.kt" />
          </div>
        </AnimateIn>
      </section>

      {/* Stats bar */}
      <section className="border-y border-[var(--aurora-panel-border)] bg-white/40">
        <div className="max-w-6xl mx-auto px-6 py-8 grid grid-cols-2 md:grid-cols-4 gap-6 text-center">
          {[
            { value: 13, suffix: "", label: "Modules" },
            { value: 124, suffix: "+", label: "Tests" },
            { value: 5000, suffix: "+", label: "Lines of Kotlin" },
            { value: 0, suffix: "", label: "External UI deps" },
          ].map((stat, i) => (
            <AnimateIn key={stat.label} delay={i * 100} direction="up">
              <div>
                <div className="text-3xl font-bold text-[var(--aurora-text)]">
                  <CountUp end={stat.value} suffix={stat.suffix} />
                </div>
                <div className="text-sm text-[var(--aurora-text-secondary)] mt-1">
                  {stat.label}
                </div>
              </div>
            </AnimateIn>
          ))}
        </div>
      </section>

      {/* Features */}
      <section id="features" className="py-24 px-6">
        <div className="max-w-6xl mx-auto">
          <AnimateIn direction="up" className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold tracking-tight mb-4">
              Everything you need to ship
            </h2>
            <p className="text-[var(--aurora-text-secondary)] text-lg max-w-xl mx-auto">
              From style tokens to background tasks — one framework, one
              convention, zero assembly required.
            </p>
          </AnimateIn>
          <FeatureGrid />
        </div>
      </section>

      {/* Code sections */}
      <section id="code" className="py-24 px-6 bg-white/40">
        <div className="max-w-6xl mx-auto space-y-32">
          {/* Before/After comparison */}
          <div>
            <AnimateIn direction="up" className="text-center mb-12">
              <h2 className="text-3xl md:text-4xl font-bold tracking-tight mb-4">
                Less boilerplate. Same platform.
              </h2>
              <p className="text-[var(--aurora-text-secondary)] text-lg max-w-xl mx-auto">
                Fluxa sits on Compose — you keep full platform access while writing
                dramatically less code.
              </p>
            </AnimateIn>
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
              <AnimateIn direction="left">
                <div>
                  <div className="text-xs font-semibold uppercase tracking-wider text-red-500 mb-3">
                    Raw Compose
                  </div>
                  <CodeBlock code={comparisonBefore} filename="Before.kt" />
                </div>
              </AnimateIn>
              <AnimateIn direction="right">
                <div>
                  <div className="text-xs font-semibold uppercase tracking-wider text-[var(--aurora-success-text)] mb-3">
                    Fluxa
                  </div>
                  <CodeBlock code={comparisonAfter} filename="After.kt" />
                </div>
              </AnimateIn>
            </div>
          </div>

          {/* Style DSL */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <AnimateIn direction="left">
              <div>
                <h3 className="text-2xl font-bold mb-4">
                  Styling that compiles
                </h3>
                <p className="text-[var(--aurora-text-secondary)] leading-relaxed mb-6">
                  Utility-first DSL with theme tokens, state variants, responsive
                  breakpoints, and motion — all type-checked by the Kotlin
                  compiler. Styles compile to a platform-agnostic IR that the
                  Compose renderer maps to modifiers.
                </p>
                <div className="flex flex-wrap gap-2">
                  {["Tokens", "Variants", "Responsive", "Motion", "Shadows", "Borders"].map(
                    (tag, i) => (
                      <span
                        key={tag}
                        className="px-3 py-1 rounded-full bg-[var(--aurora-pill)] text-[var(--aurora-pill-text)] text-xs font-medium hover:scale-105 transition-transform cursor-default"
                      >
                        {tag}
                      </span>
                    )
                  )}
                </div>
              </div>
            </AnimateIn>
            <AnimateIn direction="right" delay={150}>
              <CodeBlock code={styleCode} filename="FluxaStyles.kt" />
            </AnimateIn>
          </div>

          {/* State management */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <AnimateIn direction="left">
              <CodeBlock code={storeCode} filename="NoteStore.kt" />
            </AnimateIn>
            <AnimateIn direction="right" delay={150}>
              <div>
                <h3 className="text-2xl font-bold mb-4">
                  State you can reason about
                </h3>
                <p className="text-[var(--aurora-text-secondary)] leading-relaxed mb-6">
                  FluxaStore with pure reducers, composable middleware, and derived
                  selectors. Thread-safe by default. collectAsState() bridges
                  directly to Compose recomposition. No ViewModel ceremony, no
                  Flow operators to learn.
                </p>
                <div className="flex flex-wrap gap-2">
                  {["Pure reducers", "Middleware", "Selectors", "Thread-safe", "collectAsState"].map(
                    (tag) => (
                      <span
                        key={tag}
                        className="px-3 py-1 rounded-full bg-emerald-100 text-emerald-800 text-xs font-medium hover:scale-105 transition-transform cursor-default"
                      >
                        {tag}
                      </span>
                    )
                  )}
                </div>
              </div>
            </AnimateIn>
          </div>

          {/* Navigation */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <AnimateIn direction="left">
              <div>
                <h3 className="text-2xl font-bold mb-4">
                  Navigation without the ceremony
                </h3>
                <p className="text-[var(--aurora-text-secondary)] leading-relaxed mb-6">
                  Typed routes as Kotlin classes. Push, pop, replace, popTo — all
                  on an observable back stack. Animated transitions and system back
                  button handling come free. Deep link resolution maps URI
                  patterns to typed routes.
                </p>
                <div className="flex flex-wrap gap-2">
                  {["Typed routes", "Back stack", "Animated", "Deep links", "Back button"].map(
                    (tag) => (
                      <span
                        key={tag}
                        className="px-3 py-1 rounded-full bg-purple-100 text-purple-800 text-xs font-medium hover:scale-105 transition-transform cursor-default"
                      >
                        {tag}
                      </span>
                    )
                  )}
                </div>
              </div>
            </AnimateIn>
            <AnimateIn direction="right" delay={150}>
              <CodeBlock code={navCode} filename="Routes.kt" />
            </AnimateIn>
          </div>

          {/* CLI */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <AnimateIn direction="left">
              <CodeBlock code={cliCode} language="bash" filename="Terminal" />
            </AnimateIn>
            <AnimateIn direction="right" delay={150}>
              <div>
                <h3 className="text-2xl font-bold mb-4">
                  Zero to running in seconds
                </h3>
                <p className="text-[var(--aurora-text-secondary)] leading-relaxed mb-6">
                  The Fluxa CLI scaffolds complete projects with FluxaTheme,
                  FluxaNode screens, and proper build configuration. Generate
                  screens, components, feature modules, and stores — all
                  following framework conventions.
                </p>
                <div className="flex flex-wrap gap-2">
                  {["fluxa new", "generate screen", "generate component", "generate feature"].map(
                    (tag) => (
                      <span
                        key={tag}
                        className="px-3 py-1 rounded-full bg-amber-100 text-amber-800 text-xs font-medium font-mono hover:scale-105 transition-transform cursor-default"
                      >
                        {tag}
                      </span>
                    )
                  )}
                </div>
              </div>
            </AnimateIn>
          </div>
        </div>
      </section>

      {/* Module architecture */}
      <section id="modules" className="py-24 px-6">
        <div className="max-w-4xl mx-auto">
          <AnimateIn direction="up" className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold tracking-tight mb-4">
              13 modules. One system.
            </h2>
            <p className="text-[var(--aurora-text-secondary)] text-lg max-w-xl mx-auto">
              Clean package boundaries. No circular dependencies. Each module
              does one thing well.
            </p>
          </AnimateIn>
          <ModuleMap />
        </div>
      </section>

      {/* CTA */}
      <section className="relative py-24 px-6 bg-[var(--aurora-spotlight)] text-white overflow-hidden">
        {/* Subtle glow behind CTA */}
        <div className="absolute inset-0 pointer-events-none" aria-hidden="true">
          <div
            className="absolute w-[600px] h-[600px] rounded-full opacity-[0.08] blur-[120px]"
            style={{
              background: "radial-gradient(circle, #2563eb 0%, transparent 70%)",
              top: "-200px",
              left: "50%",
              transform: "translateX(-50%)",
            }}
          />
        </div>

        <AnimateIn direction="up">
          <div className="max-w-3xl mx-auto text-center relative z-10">
            <h2 className="text-3xl md:text-5xl font-bold tracking-tight mb-6">
              Build your next Android app with Fluxa
            </h2>
            <p className="text-lg text-[#8A96A6] mb-10 max-w-xl mx-auto">
              Open source. Kotlin-first. Built for teams that care about
              developer experience and visual quality.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <a
                href="https://github.com/MartinPirate/fluxa"
                target="_blank"
                rel="noopener noreferrer"
                className="px-8 py-3.5 rounded-xl bg-white text-[var(--aurora-spotlight)] font-semibold hover:scale-[1.03] active:scale-[0.98] transition-transform"
              >
                View on GitHub
              </a>
              <a
                href="https://github.com/MartinPirate/fluxa/blob/main/docs/vision.md"
                target="_blank"
                rel="noopener noreferrer"
                className="px-8 py-3.5 rounded-xl border border-[#2E3D50] text-white font-semibold hover:bg-[#1a2433] hover:scale-[1.02] active:scale-[0.98] transition-all"
              >
                Read the Vision
              </a>
            </div>
          </div>
        </AnimateIn>
      </section>

      {/* Footer */}
      <footer className="border-t border-[var(--aurora-panel-border)] py-8 px-6">
        <div className="max-w-6xl mx-auto flex flex-col md:flex-row items-center justify-between gap-4 text-sm text-[var(--aurora-text-secondary)]">
          <div>Fluxa Framework v0.1.0-dev</div>
          <div className="flex gap-6">
            <a
              href="https://github.com/MartinPirate/fluxa"
              target="_blank"
              rel="noopener noreferrer"
              className="hover:text-[var(--aurora-text)] transition-colors"
            >
              GitHub
            </a>
            <a
              href="https://github.com/MartinPirate/fluxa/blob/main/docs/architecture.md"
              target="_blank"
              rel="noopener noreferrer"
              className="hover:text-[var(--aurora-text)] transition-colors"
            >
              Architecture
            </a>
            <a
              href="https://github.com/MartinPirate/fluxa/blob/main/docs/roadmap.md"
              target="_blank"
              rel="noopener noreferrer"
              className="hover:text-[var(--aurora-text)] transition-colors"
            >
              Roadmap
            </a>
          </div>
        </div>
      </footer>
    </main>
  );
}
