package app.trian.tudu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import app.trian.tudu.base.BaseMainApp
import app.trian.tudu.base.EventListener
import app.trian.tudu.base.extensions.addOnEventListener
import app.trian.tudu.base.extensions.listenChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var eventListener: EventListener
    private lateinit var appState: ApplicationState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            eventListener = EventListener()
            appState = rememberApplicationState(
                event = eventListener
            )
            LaunchedEffect(key1 = appState.router, block = {
                appState.listenChanges()
                listen()

            })
            BaseMainApp(appState = appState) {
                AppNavigation(applicationState = it)
            }
        }

    }

    private fun listen(){
        appState.addOnEventListener{
            if(it == "EXIT"){
                finish()
            }
        }
    }
}

