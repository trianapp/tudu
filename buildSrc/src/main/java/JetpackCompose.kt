/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

object JetpackCompose {

    val composeBom  by lazy { "androidx.compose:compose-bom:2022.10.00" }
    val material3 by lazy{"androidx.compose.material3:material3"}
    val ui by lazy { "androidx.compose.ui:ui" }
    val materialIconExtended by lazy{"androidx.compose.material:material-icons-extended"}
    val materialWindowSizeClass by lazy{"androidx.compose.material3:material3-window-size-class"}
    val uiToolingPreview by lazy{"androidx.compose.ui:ui-tooling-preview"}
    val uiTooling by lazy{"androidx.compose.ui:ui-tooling"}
    val uiTestJunit4 by lazy{"androidx.compose.ui:ui-test-junit4"}
    val uiTestManifest by lazy{"androidx.compose.ui:ui-test-manifest"}

    val composeRuntimeLiveData by lazy {"androidx.compose.runtime:runtime-livedata:1.3.2"}
}