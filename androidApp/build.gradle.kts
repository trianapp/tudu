@file:Suppress("UnstableApiUsage")
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("io.gitlab.arturbosch.detekt")
    id("app.cash.sqldelight")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = AppConfig.nameSpace
    compileSdk = 33
    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = 24
        targetSdk = 33
        versionCode = 35
        versionName = "2.0.202303261339"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
        abortOnError = false
    }


    signingConfigs {
        create("release") {
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
            isShrinkResources = true
            isMinifyEnabled=true
            isDebuggable=false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        )
    }

}

dependencies {
    coreLibraryDesugaring(DesugarJdkLibs.desugarJdkLib)

    implementation(AndroidX.Core.coreKtx)
    implementation(AndroidX.Lifecycle.runtimeLifecycleKtx)
    implementation(AndroidX.Activity.activityCompose)
    implementation(AndroidX.Multidex.multidex)
    implementation(AndroidX.Navigation.navigationCompose)
    implementation(CoilKt.coilCompose)
    implementation(ComposeMarkdown.composeMarkdown)
    implementation(ComposeCalendar.composeCalendar)
    implementation(AndroidChart.mpAndroidChart)
    implementation(DateTimePicker.wheelPicker)

    with(SheetComposeDialog){
        implementation(core)
        implementation(dateTime)
    }
    with(SQLDelight.Sqldelight){
        implementation(androidDriver)
    }

    with(Firebase){
        implementation(platform(firebaseBom))
        implementation(auth)
        implementation(firebaseStorage)
        implementation(analytics)
        implementation(crashAnalytics)
    }


    with(Jetbrains.Kotlinx){
        implementation(googlePlayKotlinCoroutine)
        testImplementation(kotlinxCoroutinesTest)
    }
    with(JetpackCompose) {
        implementation(platform(composeBom))
        androidTestImplementation(
            platform(
                composeBom
            )
        )
        implementation(material3)
        implementation(ui)
        implementation(uiToolingPreview)
        debugImplementation(uiTooling)
        androidTestImplementation(uiTestJunit4)
        debugImplementation(uiTestManifest)
        implementation(materialIconExtended)
        implementation(materialWindowSizeClass)
    }
    with(Accompanist) {
        implementation(pager)
        implementation(pagerIndicator)
        implementation(flowLayout)
        implementation(shimmer)
    }
    with(Hilt) {
        implementation(hiltNavigationCompose)
        implementation(hiltWork)
        implementation(hiltAndroid)
        kapt(hiltAndroidCompiler)
        kapt(hiltCompiler)
    }

    with(Google.Android.Gms){
        implementation(playServicesAuth)
        implementation(playServiceBase)
    }
    with(Worker) {
        implementation(workRuntime)
    }
    implementation(Jetbrains.Kotlinx.kotlinxCoroutineAndroid)

    debugImplementation(LeakCanary.leakCanary)

    testImplementation("junit:junit:4.13.2")
}
sqldelight{
    databases{
        create("Database"){
            packageName.set("app.trian.tudu.sqldelight")
        }
    }
}
kapt {
    correctErrorTypes = true
}
tasks.create<Copy>("installGitHook") {
    var suffix = "macos"
    if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)) {
        suffix = "windows"
    }

    copy {
        from(File(rootProject.rootDir, "scripts/pre-push-$suffix"))
        into { File(rootProject.rootDir, ".git/hooks") }
        rename("pre-push-$suffix", "pre-push")
    }
    copy {
        from(File(rootProject.rootDir, "scripts/pre-commit-$suffix"))
        into { File(rootProject.rootDir, ".git/hooks") }
        rename("pre-commit-$suffix", "pre-commit")
    }
    fileMode = "775".toInt(8)
}