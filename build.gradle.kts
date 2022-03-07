buildscript {

    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")

        // Check that you have the Google services Gradle plugin v4.3.2 or later
        // (if not, add it).
        classpath("com.google.gms:google-services:4.3.10")

        // Add the Crashlytics Gradle plugin
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
    }
}
plugins {
    id("com.android.application") version "7.2.0-beta01" apply false
    id("com.android.library") version "7.2.0-beta01" apply false
    kotlin("android") version "1.6.10" /*"1.5.31"*/ apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.10" apply false
}
tasks.create<Delete>("cleanRp"){
    delete(
        rootProject.buildDir
    )
}
