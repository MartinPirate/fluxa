"use client";

import { AnimateIn } from "./AnimateIn";

const features = [
  {
    title: "Utility-First Styling",
    description:
      "Style DSL with tokens, variants, responsive breakpoints, and motion — all type-safe. No more modifier chain spaghetti.",
    icon: "paintbrush",
  },
  {
    title: "Typed Navigation",
    description:
      "FluxaRouter with typed routes, animated transitions, deep link resolution, and system back button — zero boilerplate.",
    icon: "route",
  },
  {
    title: "Unidirectional State",
    description:
      "FluxaStore with pure reducers, composable middleware, and derived selectors. Thread-safe by default.",
    icon: "layers",
  },
  {
    title: "Declarative Components",
    description:
      "Build screens as pure functions returning FluxaNode trees. Components compose, themes propagate, events bubble.",
    icon: "grid",
  },
  {
    title: "Dark Mode in One Line",
    description:
      "Aurora light and dark themes built in. Swap themes at runtime — every component adapts automatically.",
    icon: "moon",
  },
  {
    title: "CLI Scaffolding",
    description:
      "fluxa new, fluxa generate screen, fluxa generate component. Start building in seconds, not hours.",
    icon: "terminal",
  },
  {
    title: "Event System",
    description:
      "onClick, onValueChange, onCheckedChange — attach handlers directly to nodes. The renderer wires them to Compose.",
    icon: "zap",
  },
  {
    title: "Full Stack",
    description:
      "HTTP client with interceptors, TTL cache, DataStore persistence, WorkManager tasks, lifecycle effects — all integrated.",
    icon: "package",
  },
];

const iconMap: Record<string, string> = {
  paintbrush: "M9.53 16.122a3 3 0 0 0-5.78 1.128 2.25 2.25 0 0 1-2.4 2.245 4.5 4.5 0 0 0 8.4-2.245c0-.399-.078-.78-.22-1.128Zm0 0a15.998 15.998 0 0 0 3.388-1.62m-5.043-.025a15.994 15.994 0 0 1 1.622-3.395m3.42 3.42a15.995 15.995 0 0 0 4.764-4.648l3.876-5.814a1.151 1.151 0 0 0-1.597-1.597L14.146 6.32a15.996 15.996 0 0 0-4.649 4.763m3.42 3.42a6.776 6.776 0 0 0-3.42-3.42",
  route: "M9 6.75V15m6-6v8.25m.503 3.498 4.875-2.437c.381-.19.622-.58.622-1.006V4.82c0-.836-.88-1.38-1.628-1.006l-3.869 1.934c-.317.159-.69.159-1.006 0L9.503 3.252a1.125 1.125 0 0 0-1.006 0L3.622 5.689C3.24 5.88 3 6.27 3 6.695V19.18c0 .836.88 1.38 1.628 1.006l3.869-1.934c.317-.159.69-.159 1.006 0l4.994 2.497c.317.158.69.158 1.006 0Z",
  layers: "M6.429 9.75 2.25 12l4.179 2.25m0-4.5 5.571 3 5.571-3m-11.142 0L2.25 7.5 12 2.25l9.75 5.25-4.179 2.25m0 0L21.75 12l-4.179 2.25m0 0 4.179 2.25L12 21.75 2.25 16.5l4.179-2.25m11.142 0-5.571 3-5.571-3",
  grid: "M3.75 6A2.25 2.25 0 0 1 6 3.75h2.25A2.25 2.25 0 0 1 10.5 6v2.25a2.25 2.25 0 0 1-2.25 2.25H6a2.25 2.25 0 0 1-2.25-2.25V6ZM3.75 15.75A2.25 2.25 0 0 1 6 13.5h2.25a2.25 2.25 0 0 1 2.25 2.25V18a2.25 2.25 0 0 1-2.25 2.25H6A2.25 2.25 0 0 1 3.75 18v-2.25ZM13.5 6a2.25 2.25 0 0 1 2.25-2.25H18A2.25 2.25 0 0 1 20.25 6v2.25A2.25 2.25 0 0 1 18 10.5h-2.25a2.25 2.25 0 0 1-2.25-2.25V6ZM13.5 15.75a2.25 2.25 0 0 1 2.25-2.25H18a2.25 2.25 0 0 1 2.25 2.25V18A2.25 2.25 0 0 1 18 20.25h-2.25a2.25 2.25 0 0 1-2.25-2.25v-2.25Z",
  moon: "M21.752 15.002A9.72 9.72 0 0 1 18 15.75c-5.385 0-9.75-4.365-9.75-9.75 0-1.33.266-2.597.748-3.752A9.753 9.753 0 0 0 3 11.25C3 16.635 7.365 21 12.75 21a9.753 9.753 0 0 0 9.002-5.998Z",
  terminal: "m6.75 7.5 3 2.25-3 2.25m4.5 0h3m-9 8.25h13.5A2.25 2.25 0 0 0 21 18V6a2.25 2.25 0 0 0-2.25-2.25H5.25A2.25 2.25 0 0 0 3 6v12a2.25 2.25 0 0 0 2.25 2.25Z",
  zap: "m3.75 13.5 10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75Z",
  package: "m21 7.5-9-5.25L3 7.5m18 0-9 5.25m9-5.25v9l-9 5.25M3 7.5l9 5.25M3 7.5v9l9 5.25m0-9v9",
};

export function FeatureGrid() {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
      {features.map((feature, i) => (
        <AnimateIn key={feature.title} delay={i * 80} direction="up">
          <div className="feature-card group p-6 rounded-2xl bg-white/60 border border-[var(--aurora-panel-border)] hover:bg-white hover:border-[var(--aurora-panel-accent)] cursor-default">
            <div className="w-10 h-10 rounded-xl bg-[var(--aurora-pill)] flex items-center justify-center mb-4 group-hover:bg-[var(--aurora-panel-accent)] group-hover:scale-110 transition-all duration-300">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="w-5 h-5 text-[var(--aurora-pill-text)]"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d={iconMap[feature.icon]}
                />
              </svg>
            </div>
            <h3 className="text-base font-semibold mb-2 text-[var(--aurora-text)]">
              {feature.title}
            </h3>
            <p className="text-sm text-[var(--aurora-text-secondary)] leading-relaxed">
              {feature.description}
            </p>
          </div>
        </AnimateIn>
      ))}
    </div>
  );
}
