# 🧰 AndroidToolkit

A modular Android library collection containing ready-to-use Jetpack Compose components and utilities.

Each module in this repository is published and distributed **independently** via JitPack, allowing developers to import only what they need.

---

## 📦 Available Modules

| Module | Description | Dependency Coordinate | Documentation |
| :--- | :--- | :--- | :--- |
| **`:calendar`** | Responsive Compose Calendar, Dialogs & DatePicker Field | `com.github.veeramaniamk.AndroidToolkit:calendar:1.0.2` | [📖 Read Docs](docs/CALENDAR.md) |
| **`:sample`** | Sample utility module | `com.github.veeramaniamk.AndroidToolkit:sample:1.0.2` | [📖 Read Docs](docs/MODULE_TEMPLATE.md) |
| *(More coming soon)* | Add new modules seamlessly | `com.github.veeramaniamk.AndroidToolkit:<module>:<tag>` | [Template](docs/MODULE_TEMPLATE.md) |

---

## 🚀 Quick Setup for Consumers

### 1. Enable JitPack Repository
In the consuming project's `settings.gradle.kts`:

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

### 2. Add Desired Module
In your app's `build.gradle.kts`:

```kotlin
dependencies {
    // Import ONLY the Calendar module
    implementation("com.github.veeramaniamk.AndroidToolkit:calendar:1.0.2")
}
```

---

## 🗓️ Calendar Module Preview

The `:calendar` module provides 3 ready-to-use UI flavors:
1. **`CalendarView`** – Fully responsive inline calendar with swipe navigation.
2. **`CalendarDatePickerField`** – Clean form input text field with date formatting and clear button.
3. **`CalendarDialog`** – Animated modal dialog popup with spring physics.

```kotlin
// Example: Embed inline Calendar
CalendarView(
    state = rememberCalendarState(),
    onDateSelected = { date ->
        println("Selected date: $date")
    }
)
```

👉 [**Full Calendar Documentation & Examples**](docs/CALENDAR.md)

---

## 🛠️ Maintainer & Contribution Guides

- [JitPack Multi-Module Publishing Guide](JITPACK_PUBLISHING_GUIDE.md) — How to configure Gradle, publish, and release tags from scratch.
- [New Module Documentation Template](docs/MODULE_TEMPLATE.md) — Template for documenting future modules.
