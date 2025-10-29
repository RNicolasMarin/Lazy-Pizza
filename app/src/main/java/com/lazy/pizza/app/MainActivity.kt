package com.lazy.pizza.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.lazy.pizza.core.presentation.designsystem.LazyPizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        installSplashScreen()

        setContent {
            LazyPizzaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationRoot(
                        onBack = {
                            finish()
                        },
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}