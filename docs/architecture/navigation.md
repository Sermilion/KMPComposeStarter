# Navigation

## Navigation Model

Navigation is state-driven and intentionally explicit.

`StarterNavigationState` owns:

- the unauthenticated auth-flow back stack
- one back stack per authenticated top-level tab
- the current tab
- whether the user is authenticated

`StarterNavigator` is the small coordinator that mutates that state.

## Route Types

The shared route model distinguishes between:

- `AuthFlowRoute`
- `TopLevelRoute`
- `UserScopedRoute`

That split keeps auth-only destinations separate from routes that only make sense for signed-in users.

## Behavioral Rules

### Logged-out flow

When the user is not authenticated, only auth-flow routes should be pushed.

### Logged-in flow

When the user is authenticated, top-level tabs each own their own back stack.

Switching tabs preserves each tab's stack.

Re-selecting the current tab resets that tab back to its start destination.

### Back behavior

Back navigation pops within the current auth stack or current tab stack.

If the active stack has only one element left, back returns `false` so the host can decide what to do next.

## Adding New Routes

- Define typed routes with serialization support.
- Register serializers in the shared navigation serialization module.
- Add destination entries through the shared entry provider pattern.
- Pass stable identifiers rather than full mutable domain objects whenever a deeper layer remains the source of truth.

## Why this structure exists

This pattern keeps navigation rules easy to debug and easy to document.

It also matches the kind of explicit back stack ownership that scales better once auth state, tabs, and deep links become more complex.
