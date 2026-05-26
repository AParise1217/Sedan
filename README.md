# Sedan — Sequential Edit DistANce

Structural diff for Java Maps — compute the minimum add/update/delete/move operations to transform one Map into another.

[![CI](https://github.com/AParise1217/Sedan/actions/workflows/ci.yml/badge.svg)](https://github.com/AParise1217/Sedan/actions/workflows/ci.yml)
[![Javadoc](https://img.shields.io/badge/javadoc-latest-blue)](https://aparise1217.github.io/Sedan/)

---

## What it does

Sedan computes a Levenshtein-like edit distance between two `Map<String, Object>` instances. It returns a list of typed `SedanOperation` instances — add, update, delete, or move — representing the minimum changes needed to transform the source map into the target. The result can be applied back to the source map to reproduce the target exactly.

---

## Requirements

- Java 26+ (for `sedan-core`; `sedan-spring-boot-autoconfigure` targets Java 21)
- Maven 3.9+ (for building from source)

---

## Modules

| Module | Artifact | Purpose |
|---|---|---|
| `sedan-core` | `sedan-core` | Core diff/patch algorithm — no external runtime dependencies |
| `sedan-jackson` | `sedan-jackson` | Jackson serialization for `SedanOperation` |
| `sedan-spring-boot-autoconfigure` | `sedan-spring-boot-autoconfigure` | Spring Boot auto-configuration |
| `sedan-spring-boot-starter` | `sedan-spring-boot-starter` | Starter POM — include this to pull in core + autoconfigure |

---

## Quick Start

### Maven — core only

```xml
<dependency>
    <groupId>com.parisesoftware.sedan</groupId>
    <artifactId>sedan-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### Maven — Spring Boot starter

```xml
<dependency>
    <groupId>com.parisesoftware.sedan</groupId>
    <artifactId>sedan-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

A `SedanDriver` bean is automatically registered in your application context. Override it by declaring your own `@Bean`.

### Maven — Jackson serialization

```xml
<dependency>
    <groupId>com.parisesoftware.sedan</groupId>
    <artifactId>sedan-jackson</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Register `SedanModule` with your `ObjectMapper`:

```java
ObjectMapper mapper = new ObjectMapper().registerModule(new SedanModule());
```

### Building from source

```bash
git clone https://github.com/AParise1217/Sedan.git
cd Sedan
mvn package
```

---

## Usage Examples

### Example 1 — Basic diff

```java
import com.parisesoftware.sedan.SedanDriver;
import com.parisesoftware.sedan.operation.SedanOperation;
import java.util.List;
import java.util.Map;

SedanDriver driver = new SedanDriver();

Map<String, Object> source = Map.of("name", "Alice", "role", "user");
Map<String, Object> target = Map.of("name", "Alice", "role", "admin", "city", "NYC");

List<SedanOperation> ops = driver.difference(source, target);
// ops: [UpdateSedanOperation[name=role, value=admin], AdditionSedanOperation[name=city, value=NYC]]
```

### Example 2 — DiffResult views

```java
import com.parisesoftware.sedan.DiffResult;

DiffResult diff = driver.diff(source, target);

diff.additions();  // [AdditionSedanOperation[name=city, value=NYC]]
diff.updates();    // [UpdateSedanOperation[name=role, value=admin]]
diff.deletions();  // []
diff.moves();      // []
diff.areEqual();   // false
diff.isEmpty();    // false
```

### Example 3 — Apply operations (patch source to target)

```java
Map<String, Object> patched = driver.apply(source, diff.operations());
// patched.equals(target) == true
```

### Example 4 — MOVE detection

When a key is renamed but its value is unchanged, Sedan emits a `MoveSedanOperation` instead of a delete + add pair:

```java
Map<String, Object> before = Map.of("hero", "Thor");
Map<String, Object> after  = Map.of("champion", "Thor");

List<SedanOperation> ops = driver.difference(before, after);
// ops: [MoveSedanOperation[sourceName=hero, targetName=champion, value=Thor]]

Map<String, Object> patched = driver.apply(before, ops);
// patched: {champion=Thor}
```

### Example 5 — Jackson serialization

```java
ObjectMapper mapper = new ObjectMapper().registerModule(new SedanModule());

String json = mapper.writeValueAsString(new AdditionSedanOperation("city", "NYC"));
// {"type":"ADD","name":"city","value":"NYC"}

SedanOperation op = mapper.readValue(json, SedanOperation.class);
// AdditionSedanOperation[name=city, value=NYC]
```

### Example 6 — Custom data type adapter

```java
import com.parisesoftware.sedan.data.SedanDataAdapter;
import com.parisesoftware.sedan.data.SedanDataFactory;
import com.parisesoftware.sedan.data.SedanDataSource;

SedanDataFactory.getRegistry().registerAdapter(new SedanDataAdapter<MyRecord>() {
    @Override public boolean supports(Class<?> type) { return MyRecord.class.isAssignableFrom(type); }
    @Override public SedanDataSource adapt(MyRecord source) { return new MyRecordDataSource(source); }
});
```

---

## API Reference

### `SedanDriver`

| Method | Returns | Description |
|---|---|---|
| `difference(Map source, Map target)` | `List<SedanOperation>` | Compute minimum edit operations |
| `diff(Map source, Map target)` | `DiffResult` | Same as `difference` but returns a rich result wrapper |
| `apply(Map source, List<SedanOperation> ops)` | `Map<String, Object>` | Apply operations to source, returning a new unmodifiable map |

### `DiffResult`

| Method | Returns | Description |
|---|---|---|
| `operations()` | `List<SedanOperation>` | All operations (immutable) |
| `additions()` | `List<AdditionSedanOperation>` | Filter to ADD operations only |
| `updates()` | `List<UpdateSedanOperation>` | Filter to UPDATE operations only |
| `deletions()` | `List<DeletionSedanOperation>` | Filter to DELETE operations only |
| `moves()` | `List<MoveSedanOperation>` | Filter to MOVE operations only |
| `isEmpty()` | `boolean` | True when no operations were produced |
| `areEqual()` | `boolean` | True when source and target are equal |

### `SedanOperation` sealed hierarchy

| Type | `type()` | `name()` | `value()` |
|---|---|---|---|
| `AdditionSedanOperation` | `ADD` | key being added | new value |
| `UpdateSedanOperation` | `UPDATE` | key being changed | new value |
| `DeletionSedanOperation` | `DELETE` | key being removed | always `null` |
| `MoveSedanOperation` | `MOVE` | source key (`sourceName()`) | value moved; `targetName()` for destination key |

---

## Extending Sedan

Sedan's data layer is open for extension via the `SedanDataAdapter<T>` interface. To support a new input type — such as a domain object, a JSON node, or a YAML map — implement the two required methods and register the adapter with the factory.

```java
public class MyRecordAdapter implements SedanDataAdapter<MyRecord> {

    @Override
    public boolean supports(Class<?> type) {
        return MyRecord.class.isAssignableFrom(type);
    }

    @Override
    public SedanDataSource adapt(MyRecord source) {
        return new MyRecordDataSource(source);
    }
}

// Register once at startup
SedanDataFactory.getRegistry().registerAdapter(new MyRecordAdapter());
```

The registry walks its adapter list in registration order and delegates to the first adapter whose `supports()` returns `true`.

---

## Development

```bash
# Compile all modules
mvn compile

# Run all tests
mvn test

# Run a single test class (in sedan-core)
mvn test -pl sedan-core -Dtest=SedanDriverTest

# Full build with coverage report
mvn verify
# Coverage report at: sedan-core/target/site/jacoco/index.html

# Generate Javadoc
mvn javadoc:javadoc
# Javadoc at: sedan-core/target/site/apidocs/index.html

# Build and install all modules to local Maven repo
mvn install
```

---

## License

MIT License — see [LICENSE](LICENSE) for details.
