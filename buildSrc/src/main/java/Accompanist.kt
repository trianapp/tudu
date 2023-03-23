/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

object Accompanist {
    private const val pagerVersion = "0.28.0"
    val pager by lazy { "com.google.accompanist:accompanist-pager:$pagerVersion" }
    val pagerIndicator by lazy { "com.google.accompanist:accompanist-pager-indicators:$pagerVersion" }
    val flowLayout by lazy { "com.google.accompanist:accompanist-flowlayout:$pagerVersion" }
}