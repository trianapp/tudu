import android.annotation.SuppressLint
import java.time.Instant
import org.gradle.language.nativeplatform.internal.BuildType
import java.util.Properties
import java.io.FileInputStream
plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("io.github.reactivecircus.app-versioning") version "1.0.0"
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")

}


val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

val enableAppVersioning = providers
    .environmentVariable("ENABLE_APP_VERSIONING")
    .forUseAtConfigurationTime()
    .getOrElse("true").toBoolean()

//https://github.com/triandamai/streamlined/blob/main/app/build.gradle.kts
appVersioning{
    enabled.set(enableAppVersioning)
    overrideVersionCode{
        _,_,_ ->
        Instant.now().epochSecond.toInt()
    }
    overrideVersionName { gitTag, _, _ ->
        "${gitTag.rawTagName} (${gitTag.commitHash})"
    }
}

android {
    compileSdk =32

    defaultConfig {
        applicationId = Version.applicationId
        minSdk =21
        targetSdk =29
        versionCode =1
        versionName ="1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        // It's not necessary to specify, but I like to keep the debug keystore
        // in SCM so all our debug builds (on all workstations) use the same
        // key for convenience
//        create("debug") {
//            storeFile =file("debug.keystore")
//        }

        //https://github.com/onmyway133/blog/issues/285
        create("release"){
                val filePath = keystoreProperties.getProperty("storeFile")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
                storeFile = file(filePath)
                storePassword = keystoreProperties.getProperty("storePassword")
        }
    }
    buildTypes {

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled=true
            isDebuggable=true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug"){
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }





    //
    lint {
        baseline = file("lint-baseline.xml")
        abortOnError =false
    }




    compileOptions {
        sourceCompatibility =JavaVersion.VERSION_1_8
        targetCompatibility =JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:1.2.0-alpha02")
    implementation("androidx.compose.material3:material3:1.0.0-alpha04")
    implementation("androidx.compose.material:material:1.2.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-alpha02")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-alpha02")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0-alpha02")

    implementation("androidx.compose.runtime:runtime-livedata:1.2.0-alpha02")

    val lifecycle_version = "2.5.0-alpha01"
    val arch_version = "2.1.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycle_version")
    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    // optional - Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:$arch_version")

    //hilt dagger (support viewModel)
    //https://developer.android.com/training/dependency-injection/hilt-android#setup
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
    //https://developer.android.com/training/dependency-injection/hilt-testing?hl=id
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.38.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")

    testImplementation("org.mockito:mockito-core:4.0.0")
    // TODO: Bump to 4.6.* after https://github.com/robolectric/robolectric/issues/6593 is fixed
    testImplementation("org.robolectric:robolectric:4.5.1")

    //navigation
    //https://developer.android.com/jetpack/compose/navigation
    implementation("androidx.navigation:navigation-compose:2.5.0-alpha01")


    //supaya bisa inject viewModel ke navigation
    //https://developer.android.com/jetpack/compose/libraries#hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    val roomVersion = "2.4.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.4.1")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.4"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    //google auth
    implementation("com.google.android.gms:play-services-auth:20.0.1")

    //allow use await() in firebase task
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.0")

    //logcat
    implementation("com.squareup.logcat:logcat:0.1")

    //icon
    implementation("br.com.devsrsouza.compose.icons.android:octicons:1.0.0")
    implementation("br.com.devsrsouza.compose.icons.android:feather:1.0.0")

    //datetime
    implementation("joda-time:joda-time:2.10.13")

    //system ui controller
        //
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.1-alpha")


//    local unit test
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1")
//    instrumentation test
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    androidTestImplementation("com.google.truth:truth:1.1")
}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}
