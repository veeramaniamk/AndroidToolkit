# Publishing Multi-Module Android Libraries to JitPack.io (From Scratch)

This guide explains how to set up, publish, and consume multiple Android library modules separately from a single repository using [JitPack.io](https://jitpack.io).

---

## 1. Overview & Naming Convention

When a repository contains multiple library modules, JitPack publishes each module as an independent artifact.

### Coordinate Format:

| Type | Format | Example |
| :--- | :--- | :--- |
| **Multi-Module Library** | `com.github.<Username>.<RepoName>:<module-name>:<tag>` | `com.github.veeramaniamk.AndroidToolkit:calendar:1.0.2` |
| **Single-Module / Root** | `com.github.<Username>:<RepoName>:<tag>` | `com.github.veeramaniamk:AndroidToolkit:1.0.1` |

> [!IMPORTANT]
> For multi-module repositories, the **Group ID** must be `com.github.<Username>.<RepoName>` (e.g. `com.github.veeramaniamk.AndroidToolkit`), and the **Artifact ID** is the individual module name (e.g. `calendar`).

---

## 2. Project Directory Structure

```text
AndroidToolkit/
├── app/                      # Demo/App module (NOT published)
│   └── build.gradle.kts
├── calendar/                 # Library module 1 (Published as :calendar)
│   └── build.gradle.kts
├── sample/                   # Library module 2 (Published as :sample)
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts          # Root build script
├── settings.gradle.kts       # Subproject inclusions
└── jitpack.yml               # JitPack server configuration
```

---

## 3. Step-by-Step Configuration from Scratch

### Step 1: Configure `jitpack.yml` (Root Directory)
Create a file named `jitpack.yml` in the root folder of your project to specify the JDK version:

```yaml
jdk:
  - openjdk17
```

---

### Step 2: Configure `settings.gradle.kts` (Root Directory)
Include your library modules:

```kotlin
rootProject.name = "AndroidToolkit"

include(":app")
include(":calendar")
include(":sample")
```

---

### Step 3: Configure Library Modules (`build.gradle.kts`)
In every library module you want to publish (e.g., `calendar/build.gradle.kts` and `sample/build.gradle.kts`), configure the `maven-publish` plugin:

#### Example: `calendar/build.gradle.kts`
```kotlin
plugins {
    alias(libs.plugins.android.library) // id("com.android.library")
    alias(libs.plugins.kotlin.compose)  // if using compose
    id("maven-publish")
}

android {
    namespace = "com.tool.calendar"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Step A: Tell AGP to create a publishing variant
    publishing {
        singleVariant("release")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Module dependencies here...
}

// Step B: Configure Maven Publication
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                // Format: com.github.<Username>.<RepoName>
                groupId = "com.github.veeramaniamk.AndroidToolkit"
                
                // Module artifact name
                artifactId = "calendar"
                
                // Dynamic version passed by JitPack during build
                version = project.version.toString()
            }
        }
    }
}
```

#### Example: `sample/build.gradle.kts`
```kotlin
plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}

android {
    namespace = "com.tool.sample"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant("release")
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.veeramaniamk.AndroidToolkit"
                artifactId = "sample"
                version = project.version.toString()
            }
        }
    }
}
```

---

### Step 4: Configure App Module (`app/build.gradle.kts`)
The `:app` module is for demo / sample purposes only and must **NOT** apply `id("maven-publish")`:

```kotlin
plugins {
    alias(libs.plugins.android.application)
}

dependencies {
    // App uses the local library module directly
    implementation(project(":calendar"))
}
```

---

## 4. Releasing & Publishing to JitPack

### Step 1: Verify Locally (Optional but Recommended)
Run the following command to test if the publishing configuration builds properly:

```bash
./gradlew clean publishToMavenLocal "-Pgroup=com.github.veeramaniamk.AndroidToolkit" "-Pversion=1.0.0"
```

### Step 2: Commit and Push Changes to GitHub
```bash
git add .
git commit -m "Configure multi-module library publishing"
git push origin master
```

### Step 3: Create and Push a Git Tag
JitPack uses Git tags as version numbers:

```bash
git tag 1.0.3
git push origin 1.0.3
```
*(Or create a new release on GitHub targeting tag `1.0.3`)*

### Step 4: Build on JitPack
1. Open [https://jitpack.io](https://jitpack.io).
2. Enter your repository URL (e.g. `veeramaniamk/AndroidToolkit`) and click **Look up**.
3. Find your tag (e.g. `1.0.3`) and click **Get it**.
4. Wait for the build icon to turn **green** (click the log icon to check the build status).

Once finished, JitPack displays all published artifacts:
```
✅ Build artifacts:
com.github.veeramaniamk.AndroidToolkit:calendar:1.0.3
com.github.veeramaniamk.AndroidToolkit:sample:1.0.3
```

---

## 5. How to Consume Specific Modules in Client Projects

In any other Android project where you want to use only one specific module:

### Step 1: Add JitPack Repository
In the client project's `settings.gradle.kts`:

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

### Step 2: Add Module Dependencies
In the client app's `build.gradle.kts`:

```kotlin
dependencies {
    // 1. To import ONLY the Calendar module:
    implementation("com.github.veeramaniamk.AndroidToolkit:calendar:1.0.3")

    // 2. To import ONLY the Sample module (if needed):
    // implementation("com.github.veeramaniamk.AndroidToolkit:sample:1.0.3")
}
```

---

## 6. Common Pitfalls & Troubleshooting

### ❌ Issue 1: "Could not find `com.github.User:module:tag`"
* **Cause:** Wrong `groupId`. JitPack requires `com.github.User.Repo` for multi-module repositories.
* **Fix:** Use `com.github.veeramaniamk.AndroidToolkit:calendar:TAG` instead of `com.github.veeramaniamk:calendar:TAG`.

### ❌ Issue 2: Single module was published as root repo name
* **Cause:** If only one module has the `maven-publish` setup, JitPack falls back to single-artifact mode (`com.github.User:Repo:tag`).
* **Fix:** Ensure all library modules to be published have the `maven-publish` configuration.

### ❌ Issue 3: Hardcoded version numbers
* **Cause:** Writing `version = "1.0.0"` in `build.gradle.kts` will clash when releasing tag `1.0.3`.
* **Fix:** Use `version = project.version.toString()`. JitPack automatically passes `-Pversion=<tag>` during CI build.

### ❌ Issue 4: Java Version incompatible error on JitPack
* **Cause:** JitPack defaults to Java 8 or 11 unless configured.
* **Fix:** Ensure `jitpack.yml` is committed at root with `jdk: [ openjdk17 ]`.
