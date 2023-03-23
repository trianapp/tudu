/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

object SQLDelight {

    val sqlDelightVersion = "2.0.0-alpha05"


    object Sqldelight {
        val coroutineExtension by lazy { "app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion" }
        val androidDriver by lazy { "app.cash.sqldelight:android-driver:$sqlDelightVersion" }
        val nativeDriver by lazy { "app.cash.sqldelight:native-driver:$sqlDelightVersion" }
    }


}