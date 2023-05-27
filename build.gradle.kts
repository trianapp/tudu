plugins {
    alias(libs.plugins.org.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.io.gitlab.arthubosch.detekt) apply false
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.com.google.services) apply false
    alias(libs.plugins.com.google.crashanalytics) apply false
}

buildscript {
    dependencies{
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
