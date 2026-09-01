# AndroidToolkit

Android Toolkit is a modular Android library containing ready-to-use Compose components and utilities.

## Published Modules

| Module | Description | Dependency Coordinate |
| :--- | :--- | :--- |
| **`:calendar`** | Compose Calendar UI and utilities | `com.github.veeramaniamk.AndroidToolkit:calendar:<tag>` |
| **`:sample`** | Sample utility module | `com.github.veeramaniamk.AndroidToolkit:sample:<tag>` |

---

## Getting Started

### 1. Add JitPack Repository

Add JitPack to your `settings.gradle.kts`:

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

### 2. Add Desired Module Dependency

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    // Import Calendar module only
    implementation("com.github.veeramaniamk.AndroidToolkit:calendar:1.0.2")

    // Or import Sample module only
    // implementation("com.github.veeramaniamk.AndroidToolkit:sample:1.0.2")
}
```

---

## Documentation

For instructions on publishing and releasing multi-module libraries from scratch, refer to the [JitPack Publishing Guide](JITPACK_PUBLISHING_GUIDE.md).
