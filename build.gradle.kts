plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application") version "7.4.2"  apply false
    id("com.android.library") version "7.4.2" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("app.cash.sqldelight") version "2.0.0-alpha05" apply false
    id("io.gitlab.arturbosch.detekt") version "1.22.0" apply false
    kotlin("android") version "1.8.0"  apply false
    kotlin("multiplatform") version "1.8.0" apply false
    kotlin("plugin.serialization") version "1.8.21" apply false
}
buildscript {
    dependencies{
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}
extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "yes")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.getByPath(":androidApp:preBuild").dependsOn("installGitHook")