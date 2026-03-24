# Fluxa

Fluxa is an opinionated mobile app framework for Android built to make UI development faster, cleaner, and more coherent.

It starts on top of Android and Jetpack Compose, but its goal is not to be another loose bag of libraries. Fluxa aims to feel like a system:

- a better UI authoring model
- utility-first styling for mobile
- stricter app architecture defaults
- strong preview and tooling workflows

## Why

Modern Android development still asks developers to assemble too many moving parts:

- UI primitives
- state patterns
- navigation
- styling conventions
- theming
- previews
- project structure

Jetpack solves real problems, but it still leaves a lot of integration work to app teams. Fluxa exists to compress that complexity into a more opinionated and more productive developer experience.

## Product Thesis

Fluxa combines:

- Jetpack's practical power
- React-like UI ergonomics
- Tailwind-like styling speed
- stronger defaults for architecture and tooling

The first wedge is not replacing Android. The first wedge is replacing the developer experience on top of Android.

## Initial Scope

Fluxa will begin as a framework layer on top of Compose with three core pillars:

1. `fluxa-ui`
Declarative UI primitives, layout helpers, state-friendly component authoring, and animation conveniences.

2. `fluxa-style`
Utility-first styling, tokens, variants, theme support, and responsive rules for mobile layouts.

3. `fluxa-cli`
Project scaffolding, feature generation, preview workflows, and code generation for Fluxa apps.

## Principles

- Build on top of Android first
- Replace experience before replacing runtime
- Prefer one clear default over endless options
- Optimize for fast screen-building and maintainable code
- Make beautiful UI easier, not harder

## Current Status

Fluxa is in the design and foundation stage. The current focus is:

- product definition
- MVP boundaries
- module layout
- first implementation plan

## Docs

- [Vision](./docs/vision.md)
- [MVP](./docs/mvp.md)
- [Architecture](./docs/architecture.md)
- [Roadmap](./docs/roadmap.md)

## Repository Layout

- `packages/fluxa-runtime`: low-level shared runtime types
- `packages/fluxa-style`: utility-first styling and token model
- `packages/fluxa-ui`: UI composition primitives
- `packages/fluxa-cli`: scaffolding entrypoint
- `apps/showcase`: future Android and Compose proving ground
