# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this project is

**Sedan** (Sequential Edit DistANce) computes a Levenshtein-like diff between two `Map` objects, returning a list of `ISedanOperation` instances describing the minimum edits (add, update, delete) needed to transform the source into the target.

## Commands

```bash
# Build
mvn compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=SedanDriverTest

# Run a single test method (Spock feature name as a pattern)
mvn test -Dtest="SedanDriverTest#difference*"

# Full build + test
mvn package
```

## Language split

The project mixes **Java** and **Groovy** in the same Maven build. GMavenPlus compiles both. The general pattern is:

- **Java** (`src/main/java`): interfaces, value objects, adapters, enums — classes that need to be usable from both languages without Groovy runtime overhead. `OperationType`, `SedanDataSource`, `SedanDataAdapter`, `SedanDataRegistry`, `SedanDataFactory`, `MapSedanDataAdapter`, and `OperationContext` (a Java record) live here.
- **Groovy** (`src/main/groovy`): algorithm logic and factories — `SedanDriver`, `ISedanOperation`, `AbstractSedanOperation`, the three concrete operation impls, `SedanOperationFactory`, and `OperationContextAssembler`.
- **Tests** (`src/test/groovy`): all tests are Spock specs (Groovy), even for Java classes.

## Architecture

### Data layer

`SedanDriver.difference()` first wraps each raw `Map` in a `SedanDataSource` via `SedanDataFactory.construct()`. The factory delegates to `SedanDataRegistry`, which holds a list of `SedanDataAdapter<T>` strategies. The only built-in adapter is `MapSedanDataAdapter`. To support a new input type (e.g. JSON node, YAML map), register a new `SedanDataAdapter` implementation with `SedanDataFactory.getRegistry().registerAdapter(...)`.

### Operation layer

After diffing the two data sources, `SedanDriver` builds `ISedanOperation` instances via two collaborators:

1. `OperationContextAssembler` — creates an `OperationContext` record (type + name + optional value).
2. `SedanOperationFactory` — switches on `OperationType` to instantiate `AdditionSedanOperation`, `UpdateSedanOperation`, or `DeletionSedanOperation`.

All concrete operations extend `AbstractSedanOperation`, which holds `type` and `name`. Adding a new operation type requires: a new `OperationType` enum value, a new concrete class, and a new case in `SedanOperationFactory`.

### Call flow

```
SedanDriver.difference(Map source, Map target)
  → SedanDataFactory.construct(map)          → SedanDataSource
  → SedanDriver.*  (compare keys/values)
  → OperationContextAssembler.create*Context → OperationContext
  → SedanOperationFactory.construct(context) → ISedanOperation
```

## Testing conventions

Tests use [Spock Framework](https://spockframework.org/) with Groovy. Each feature method follows the given/when/then block structure. Static collaborators (`SedanOperationFactory`, `OperationContextAssembler`) are mocked with `GroovyMock(Foo, global: true)` when verifying delegation.
