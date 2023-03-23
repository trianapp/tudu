/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

object AndroidX {
    object Core{
        val coreKtx by lazy { "androidx.core:core-ktx:1.7.0" }
    }
    object Lifecycle{
        val runtimeLifecycleKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1" }
//            val runtimeLiveDataKtx by lazy {"androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"}
    }
    object Activity{
        val activityCompose by lazy{"androidx.activity:activity-compose:1.3.1"}
    }
    object Test{
        val extJunit by lazy {"androidx.test.ext:junit:1.1.3"}
        val espressoCore by lazy {"androidx.test.espresso:espresso-core:3.4.0"}
    }
    object Multidex{
        private const val multidex_version = "2.0.1"
        val multidex by lazy {"androidx.multidex:multidex:$multidex_version"}
    }
    object Navigation{
        private const val nav_version = "2.5.3"
        val navigationCompose by lazy {"androidx.navigation:navigation-compose:$nav_version"}
    }
}