# Text To Object Loader
a simple Java 17 library that provides a tool to instantiate objects from a text file.

# Main Components

## ObjectParser

`ObjectParser<T>` is a generic Java utility for parsing plain-text files into Java objects of type `T`.  
It uses reflection to map string-based attributes from a file into strongly-typed fields of the target class.

---

### Features

- Generic: works with any class that has a **no-argument constructor**.  
- Configurable: parsing is guided by a `ParserConfiguration` instance.  
- Type-safe: supports automatic conversion to common Java types:
  - Primitives (`int`, `long`, `double`, `float`, `short`, `byte`, `boolean`, `char`)
  - Wrappers (`Integer`, `Long`, `Double`, etc.)
  - `String`
  - `BigDecimal`
  - `BigInteger`
  - `LocalDateTime` (with configurable formatter)

---

## Requirements

- Java 17+ (recommended for switch expressions)  
- A properly configured `ParserConfiguration` instance  
