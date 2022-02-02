buildscript {

    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("com.google.gms:google-services:4.3.10")
    }
}

plugins {
    id("com.android.application") version "7.2.0-beta01" apply false
    id("com.android.library") version "7.2.0-beta01" apply false
    kotlin("android") version Version.KotlinVersion /*"1.5.31"*/ apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.10" apply false
}
tasks.create<Delete>("cleanRp"){
    delete(
        rootProject.buildDir
    )
}
