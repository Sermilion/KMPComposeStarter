# Conventions

## Keep the Starter Generic

This repository is a template, not a product codebase.

Avoid adding:

- company-specific secrets or endpoints
- organization-specific release flows
- product naming that future consumers must undo
- tightly coupled integrations that belong in a real app, not a starter

## Source-of-Truth Rules

- Keep domain contracts and long-lived sources of truth in shared layers.
- Do not let UI state become the only owner of data that a repository or database should own.
- When navigation or UI events refer to persistent entities, prefer stable IDs over copying full objects through multiple layers.

## Repository Design Guidance

- `core:data` should implement shared contracts instead of leaking transport or persistence details upward.
- Prefer exposing both single-item and bulk write operations when callers are likely to save collections.
- Keep mapping logic close to the data layer instead of re-implementing it in every feature.

## Compose Guidance

Adapted from the kinds of practices that scale well in larger Compose apps:

- Keep ViewModel-backed entry points thin.
- Hoist display state into reusable content composables where practical.
- Make previewable UI easy to render with sample state and minimal wiring.
- Keep side effects and navigation triggers explicit instead of burying them in deeply nested UI code.

## Navigation Guidance

- Keep route definitions typed and serializable.
- Keep auth-only and authenticated routes clearly separated.
- Preserve explicit back stack ownership rather than hiding it in custom magic.

## Persistence Guidance

- Use Room for relational local data.
- Use DataStore for small preference-style settings.
- Treat DataStore file renames as migrations, not cosmetic refactors.
- Commit Room schema exports when the schema changes.

## Documentation Workflow

When changing starter-wide behavior, update the relevant docs in the same change:

- `README.md` for user-facing setup or feature changes
- `docs/architecture/*.md` for architecture or contributor guidance
- `AGENTS.md` and `CLAUDE.md` for agent-facing workflow expectations

Documentation is part of the template contract, not optional follow-up work.
