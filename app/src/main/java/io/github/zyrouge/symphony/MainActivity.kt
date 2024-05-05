package io.github.zyrouge.symphony

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.zyrouge.symphony.ui.view.BaseView
import io.github.zyrouge.symphony.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    private var gSymphony: Symphony? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initializes two view models, ignition and symphony, using the by viewModels() delegate
        val ignition: IgnitionViewModel by viewModels()
        //If it's a new creation, it installs a splash screen and sets a condition to keep the splash screen visible until the ignition.ready value becomes true
        if (savedInstanceState == null) {
            installSplashScreen().apply {
                setKeepOnScreenCondition { !ignition.ready.value }
            }
        }
        //Sets a default uncaught exception handler to log the error, start the ErrorActivity with the error information, and finish the current activity.
        Thread.setDefaultUncaughtExceptionHandler { _, err ->
            Logger.error("MainActivity", "Uncaught exception", err)
            ErrorActivity.start(this, err)
            finish()
        }
        //viewModel
        val symphony: Symphony by viewModels()
        //Handles the permission information using the symphony.permission.handle() method.
        symphony.permission.handle(this)
        //Sets the gSymphony global variable to the symphony instance
        gSymphony = symphony
        //Calls the symphony.ready() method.
        symphony.ready()
        //Attaches event handlers using the attachHandlers() method.
        attachHandlers()

        // Allow app to draw behind system bar decorations (e.g.: navbar)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // NOTE: disables action bar on orientation changes (esp. in miui)
        actionBar?.hide()
        setContent {
            //Launches an effect that checks if the ignition view model is not ready and calls the ignition.toReady() method if it's not.
            LaunchedEffect(LocalContext.current) {
                if (!ignition.isReady) {
                    ignition.toReady()
                }
            }
            //Sets the content of the activity to the BaseView composable, passing the symphony view model and the current activity as parameters.
            BaseView(symphony = symphony, activity = this)
        }
    }

    override fun onPause() {
        super.onPause()
        gSymphony?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        gSymphony?.destroy()
    }

    private fun attachHandlers() {
        gSymphony?.closeApp = {
            finish()
        }
    }
}

class IgnitionViewModel : ViewModel() {
    private val readyFlow = MutableStateFlow(false)
    val ready = readyFlow.asStateFlow()
    val isReady: Boolean
        get() = readyFlow.value

    fun toReady() {
        if (readyFlow.value) return
        viewModelScope.launch { readyFlow.emit(true) }
    }
}
