/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

object Ktor {
    object IO {
        object Ktor {
            val contentNegotioton by lazy { "io.ktor:ktor-client-content-negotiation:2.2.3" }
            val ktorClientCore by lazy { "io.ktor:ktor-client-core:2.2.3" }
            val ktorAndroidClient by lazy { "io.ktor:ktor-client-okhttp:2.2.3" }
            val ktorIosClient by lazy { "io.ktor:ktor-client-darwin:2.2.3" }
            val gson by lazy { "io.ktor:ktor-serialization-gson:2.2.3" }
            val json by lazy { "io.ktor:ktor-serialization-kotlinx-json:2.2.3" }
            val resource by lazy { "io.ktor:ktor-client-resources:2.2.3" }
        }
    }
}