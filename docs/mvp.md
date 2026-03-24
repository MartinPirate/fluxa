# Fluxa MVP

## MVP Goal

Ship the smallest version of Fluxa that makes building Android screens meaningfully better than raw Compose for an early adopter.

The MVP should prove:

- Fluxa can improve UI ergonomics
- Fluxa can make styling faster
- Fluxa can provide a cleaner starting structure

## MVP Definition

Fluxa v0 focuses on:

- `fluxa-ui`
- `fluxa-style`
- `fluxa-cli`

It does not attempt a full app platform yet.

## In Scope

### 1. UI Authoring Layer

- screen primitives
- stack/row/column/layout helpers
- spacing and alignment primitives
- simple animation utilities
- state-friendly component conventions
- preview-friendly APIs

### 2. Utility-First Styling

- utility classes or utility descriptors for spacing, color, typography, radius, borders, and layout
- theme token system
- variants for component states and visual modes
- light/dark theme support
- responsive rules for common breakpoints or size classes

### 3. CLI And Scaffolding

- create a Fluxa project
- generate a screen
- generate a component
- generate a feature module
- install baseline project structure

### 4. Example App

- one polished sample app
- multiple screens
- theme switching
- variants usage
- examples of responsive layout and motion

## Out Of Scope

- custom renderer
- full routing system
- data fetching framework
- offline sync engine
- auth framework
- storage abstraction suite
- background jobs abstraction
- design-to-code tooling
- production IDE plugin

## Quality Bar

The MVP must:

- feel simpler than raw Compose
- generate code that is readable
- produce visually polished examples
- avoid fragile magic
- keep extension points possible for later phases

## MVP User

The first user is a design-conscious Android developer or small product team that wants:

- faster UI iteration
- cleaner styling conventions
- stronger project defaults

## Exit Criteria

The MVP is ready when a developer can:

1. create a new app with the CLI
2. generate a screen
3. style it with Fluxa utilities and tokens
4. preview and iterate quickly
5. build a small polished app without dropping into raw Compose for common UI work
