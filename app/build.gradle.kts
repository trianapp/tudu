import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
    kotlin("kapt")

}


val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))


android {
    compileSdk =32

    defaultConfig {
        applicationId = Version.applicationId
        minSdk =21
        targetSdk =30
        versionCode =27
        versionName = "1.0.3"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        /**
         *  It's not necessary to specify, but I like to keep the debug keystore * in SCM so all our debug builds (on all workstations) use the same
         *  key for convenience
            create("debug") {
                storeFile =file("debug.keystore")
            }
         */

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
            multiDexKeepProguard = file("multidex-config.txt")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
        abortOnError =false
    }


    compileOptions {
        // Flag to enable support for the new language APIs

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

    implementation("io.github.boguszpawlowski.composecalendar:composecalendar:0.3.0")
    implementation ("com.google.android.material:material:1.5.0")
    implementation(Libs.AndroidX.Multidex.multidex)
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
    with(Libs.AndroidX.Lifecycle){
        implementation(lifecycleRuntimeKtx)
        // ViewModel
        implementation(lifecycleViewmodel)
        implementation(lifecycleLivedata)
        implementation(lifecycleRuntime)
        implementation(lifecycleViewmodelSavedstate)
    }

    testImplementation(Libs.AndroidX.Arch.Core.coreTesting)
    androidTestImplementation(Libs.AndroidX.Arch.Core.coreTesting)

    //hilt dagger (support viewModel)
    //https://developer.android.com/training/dependency-injection/hilt-android#setup
    //https://developer.android.com/training/dependency-injection/hilt-testing?hl=id
    with(Libs.Com.Google.Dagger){
        implementation(hiltAndroid)
        kapt(hiltAndroidCompiler)
        testImplementation(hiltAndroidTesting)
        kaptTest(hiltAndroidCompiler)
        androidTestImplementation(hiltAndroidTesting)
        kaptAndroidTest(hiltAndroidCompiler)

    }

    implementation(Libs.Com.Github.Jeziellago.composeMarkdown)

    testImplementation(Libs.Org.Mockito.mockitoCore)
    //  Bump to 4.6.* after https://github.com/robolectric/robolectric/issues/6593 is fixed
    testImplementation(Libs.Org.Robolectric.robolectric)

    //navigation
    //https://developer.android.com/jetpack/compose/navigation
    implementation(Libs.AndroidX.Navigation.navigationCompose)


    //supaya bisa inject viewModel ke navigation
    //https://developer.android.com/jetpack/compose/libraries#hilt
    implementation(Libs.AndroidX.Hilt.hiltNavigationCompose)

    with(Libs.AndroidX.Room){
        implementation(roomRuntime)
        implementation(roomPaging)
        implementation(roomKtx)
        annotationProcessor(roomCompiler)
        kapt(roomCompiler)
        testImplementation(roomTesting)

    }

    //firebase
    with(Libs.Com.Google.Firebase){
        implementation(platform(bom))
        implementation(auth)
        implementation(firestore)
        implementation(storage)
        implementation(messaging)
        implementation(crashlytics)
        implementation(analytics)

    }
    //google auth
    with(Libs.Com.Google.Android.Gms){
        implementation(auth)
    }

    //allow use await() in firebase task
    with(Libs.Org.Jetbrains.Kotlinx){
        implementation(googlePlayKotlinCoroutine)
    }
    //logcat
    with(Libs.Com.Squareup.Logcat){
        implementation(logcat)
    }

    //icon
    with(Libs.Br.Com.Devsrsouza.Compose.Icons.Android){
        implementation(octicons)
        implementation(feather)
    }

    //datetime
    with(Libs.JodaTime){
        implementation(jodaTime)
    }

    //system ui controller
    with(Libs.Com.Google.Accompanist){
        implementation(accompanistSystemUiController)
        implementation(accompanistNavigationAnimation)
//        implementation(accompanistPager)
    }
    //image loader
    with(Libs.Io.CoilKt){
        implementation(coilCompose)
    }

    //chart
    with(Libs.Com.Github.PhilJay){
        implementation(mpAndroidChart)
    }

    implementation(Libs.Com.Github.GrenderG.toasty)

//    local unit test
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
