plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }



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

    implementation(Libs.AndroidX.Core.coreKtx)
    implementation(Libs.AndroidX.Activity.activityCompose)
    with(Libs.AndroidX.Compose){
        implementation(Libs.AndroidX.Compose.Ui.ui)
        // https://stackoverflow.com/questions/68224361/jetpack-compose-cant-preview-after-updating-to-1-0-0-rc01
        implementation(Libs.AndroidX.Compose.Ui.uiTooling)
        implementation(Libs.AndroidX.Compose.Ui.uiToolingPreview)
        implementation(Libs.AndroidX.Compose.Material.material)
        implementation(Libs.AndroidX.Compose.Runtime.runtimeLivedata)
        androidTestImplementation(Libs.AndroidX.Compose.Ui.uiTestJunit4)
        debugImplementation(Libs.AndroidX.Compose.Ui.uiTestManifest)
        debugImplementation(Libs.AndroidX.Compose.Ui.uiTooling)

    }

    //datetime
    with(Libs.JodaTime){
        implementation(jodaTime)
    }
    testImplementation(Libs.Junit.junit)
    testImplementation(Libs.Com.Google.Truth.truth)
    androidTestImplementation(Libs.Com.Google.Truth.truth)
//    instrumentation test
    androidTestImplementation(Libs.AndroidX.Test.Ext.junit)
    androidTestImplementation(Libs.AndroidX.Test.Espresso.espressoCore)

    androidTestImplementation(Libs.Org.Jetbrains.Kotlinx.kotlinxCoroutinesTest)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
