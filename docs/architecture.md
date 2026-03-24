# Fluxa Architecture

## Initial Repository Shape

Fluxa should start as a monorepo with clear package boundaries.

## Proposed Modules

### `packages/fluxa-ui`

Responsibilities:

- declarative component primitives
- layout primitives
- animation helpers
- preview adapters
- reusable screen composition patterns

### `packages/fluxa-style`

Responsibilities:

- utility-first styling API
- design tokens
- variants
- theming
- responsive rules
- motion tokens

### `packages/fluxa-runtime`

Responsibilities:

- shared runtime glue between Fluxa APIs and Compose
- state propagation helpers
- environment/config handling
- low-level interop boundaries

Note:

This should stay small. The goal is not to hide Android entirely. The goal is to support the higher-level developer-facing APIs cleanly.

### `packages/fluxa-cli`

Responsibilities:

- app scaffolding
- screen and component generators
- project templates
- future code generation

### `apps/showcase`

Responsibilities:

- sample Fluxa app
- visual reference for components and styling
- test bed for DX decisions

## Design Constraints

- public APIs should feel consistent across packages
- generated code should be readable and editable
- package boundaries should prevent circular architecture
- styling and UI should integrate cleanly without hidden magic

## API Direction

Fluxa should prefer:

- declarative APIs
- explicit defaults
- strong composition
- predictable state flows
- readable generated project structure

Fluxa should avoid:

- over-abstracting core Android concepts
- meta-programming that hides behavior
- too many parallel ways to do the same thing

## Suggested Early Technical Stack

- Kotlin
- Compose underneath for rendering
- Gradle for builds
- a single sample app for real-world validation

## Suggested Build Order

1. repository structure
2. `fluxa-ui` primitives
3. `fluxa-style` token and utility model
4. showcase app
5. `fluxa-cli` scaffolding

## First Technical Questions

- What should a Fluxa component declaration look like?
- How should utility styling be represented in Kotlin?
- Should styling compile to modifiers, wrappers, or generated code?
- How much Compose should remain visible to app developers?
- What is the smallest preview workflow that feels materially better?
