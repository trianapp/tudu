/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

object Chucker {
    const val chuckerVersion = "3.5.2"
    val chuckerDebug by lazy{"com.github.chuckerteam.chucker:library:${chuckerVersion}"}
    val chuckerRelease by lazy{"com.github.chuckerteam.chucker:library-no-op:${chuckerVersion}"}
}