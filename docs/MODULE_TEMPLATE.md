# Module Documentation Template

> Use this template whenever you add a new library module to `AndroidToolkit`.

---

# [Module Name] (`:[module-name]`)

Brief 1-2 sentence overview of what this module provides.

---

## 📦 Dependency & Installation

### Step 1: Add JitPack Repository
Add JitPack in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add Dependency
In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.veeramaniamk.AndroidToolkit:[module-name]:<latest-tag>")
}
```

---

## ✨ Features

- Feature 1 description
- Feature 2 description
- Feature 3 description

---

## 🚀 Quick Start Examples

### Example 1: Basic Usage

```kotlin
// Code example here...
```

### Example 2: Advanced Usage

```kotlin
// Code example here...
```

---

## 🛠️ API Reference

### Component / Class Name

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `param1` | `Type` | `Default` | Description |
| `param2` | `Type` | `Default` | Description |

---

## 📋 Checklist for Adding a New Module to AndroidToolkit

1. [ ] Add `include(":[module-name]")` to [settings.gradle.kts](../settings.gradle.kts).
2. [ ] Add `id("maven-publish")` and publishing configuration to `[module-name]/build.gradle.kts`.
3. [ ] Duplicate this template to `docs/[MODULE_NAME].md` and fill in the details.
4. [ ] Add a new row to the module table in [README.md](../README.md).
5. [ ] Release a new Git tag to publish the new module to JitPack.
