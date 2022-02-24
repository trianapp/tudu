object Libs {
    object Com{
        object Google{
            //for firebase sdk
            object Firebase{
                val bom by lazy {"com.google.firebase:firebase-bom:29.0.4"}
                val auth by lazy{"com.google.firebase:firebase-auth-ktx"}
                val firestore by lazy{"com.google.firebase:firebase-firestore-ktx"}
                val storage by lazy{"com.google.firebase:firebase-storage-ktx"}
                val messaging by lazy{"com.google.firebase:firebase-messaging-ktx"}
                val crashlytics by lazy{"com.google.firebase:firebase-crashlytics-ktx"}
                val analytics by lazy {"com.google.firebase:firebase-analytics-ktx"}
            }
            //for google authentication
            object Android{
                object Gms{
                    val auth by lazy {"com.google.android.gms:play-services-auth:20.0.1"}
                }
            }

            //accompanist(external library for jetpack compose)
            object Accompanist{
                val accompanistSystemUiController by lazy{"com.google.accompanist:accompanist-systemuicontroller:0.24.1-alpha"}

                val accompanistNavigationAnimation by lazy{"com.google.accompanist:accompanist-navigation-animation:0.24.2-alpha" }

            }

            //dagger hilt
            object Dagger{
                private const val dagger_hilt_version = "2.38.1"
                val hiltAndroid by lazy{"com.google.dagger:hilt-android:$dagger_hilt_version"}
                val hiltAndroidCompiler by lazy{"com.google.dagger:hilt-android-compiler:$dagger_hilt_version"}
                val hiltAndroidTesting by lazy{"com.google.dagger:hilt-android-testing:$dagger_hilt_version"}

            }
            object Truth{
                val truth by lazy{"com.google.truth:truth:1.1"}
            }
        }
        object Github{

            object Jeziellago{
                private const val compose_version_markdown = "0.2.6"
                val composeMarkdown by lazy { "com.github.jeziellago:compose-markdown:$compose_version_markdown" }
            }
            object PhilJay{
                val mpAndroidChart by lazy{"com.github.PhilJay:MPAndroidChart:v3.1.0"}
            }
            object GrenderG{
               val toasty by lazy{ "com.github.GrenderG:Toasty:1.5.2"}
            }
        }
        object Squareup{
            //for logging in debugging mode
            object Logcat{
                val logcat by lazy {"com.squareup.logcat:logcat:0.1"}

            }
        }
    }
    object AndroidX{
        object Multidex{
            val multidex by lazy{"androidx.multidex:multidex:2.0.1"}
        }
        object Core{
            val coreKtx by lazy { "androidx.core:core-ktx:1.7.0" }
        }
        object Compose{
            const val composeVersion = "1.2.0-alpha02"
            object Ui{
                val ui by lazy{"androidx.compose.ui:ui:$composeVersion"}
                val uiToolingPreview by lazy{"androidx.compose.ui:ui-tooling-preview:$composeVersion"}
                val uiTooling by lazy{"androidx.compose.ui:ui-tooling:$composeVersion"}
                val uiTestJunit4 by lazy{"androidx.compose.ui:ui-test-junit4:$composeVersion"}
                val uiTestManifest by lazy{"androidx.compose.ui:ui-test-manifest:$composeVersion"}
            }
            object Runtime{
                val runtimeLivedata by lazy{"androidx.compose.runtime:runtime-livedata:$composeVersion"}
            }
            object Material{
                val material by lazy{"androidx.compose.material:material:$composeVersion"}
            }
        }
        object Lifecycle{
            private const val lifecycle_version = "2.5.0-alpha01"

            val lifecycleRuntimeKtx by lazy{"androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"}
            val lifecycleViewmodel by lazy{"androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"}
            val lifecycleLivedata by lazy{"androidx.lifecycle:lifecycle-livedata:$lifecycle_version"}
            val lifecycleRuntime by lazy{"androidx.lifecycle:lifecycle-runtime:$lifecycle_version"}
            val lifecycleViewmodelSavedstate by lazy{"androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"}

        }
        object Activity{
            val activityCompose by lazy{"androidx.activity:activity-compose:1.4.0"}
        }
        object Arch{
            const val arch_version = "2.1.0"
            object Core{
                val coreTesting by lazy{"androidx.arch.core:core-testing:$arch_version"}
            }
        }
        object Navigation{
            val navigationCompose by lazy {"androidx.navigation:navigation-compose:2.5.0-alpha01"}
        }
        object Hilt{
            val hiltNavigationCompose by lazy{"androidx.hilt:hilt-navigation-compose:1.0.0"}
        }
        object Room{
            private const val room_version = "2.4.1"
            val roomRuntime by lazy{"androidx.room:room-runtime:$room_version"}
            val roomCompiler by lazy {"androidx.room:room-compiler:$room_version"}
            val roomKtx by lazy{"androidx.room:room-ktx:$room_version"}
            val roomTesting by lazy {"androidx.room:room-testing:$room_version"}
            val roomPaging by lazy{"androidx.room:room-paging:$room_version"}
        }
        object Test{
            object Ext{
                val junit by lazy{"androidx.test.ext:junit:1.1.3"}
            }
            object Espresso{
                val espressoCore by lazy{"androidx.test.espresso:espresso-core:3.4.0"}
            }
        }
    }
    object Org{
        object Jetbrains{

            //for use `await()` with google and firebase Task API
            object Kotlinx{

                val googlePlayKotlinCoroutine by lazy{"org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Version.KotlinVersion}"}
                val kotlinxCoroutinesTest by lazy {"org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.KotlinVersion}"}
            }
        }
        object Mockito{
            val mockitoCore by lazy {"org.mockito:mockito-core:4.0.0"}
        }
        object Robolectric{
            val robolectric by lazy {"org.robolectric:robolectric:4.5.1"}
        }
    }
    object Br{
        object Com {
            object Devsrsouza{
                object Compose{
                    object Icons{
                        object Android{
                            val octicons by lazy {"br.com.devsrsouza.compose.icons.android:octicons:1.0.0"}
                            val feather by lazy{"br.com.devsrsouza.compose.icons.android:feather:1.0.0"}
                        }
                    }
                }
            }
        }
    }
    object JodaTime{
        val jodaTime by lazy{"joda-time:joda-time:2.10.13"}
    }
    object Io{
        object CoilKt{
            val coilCompose by lazy{"io.coil-kt:coil-compose:1.4.0"}
        }
    }
    object Junit{
        val junit by lazy {"junit:junit:4.13.2"}
    }

}

