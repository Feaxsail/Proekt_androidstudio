package com.shcherbakov.androidstudio_proekt_shcherbakov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.shcherbakov.androidstudio_proekt_shcherbakov.navigation.MovieNavGraph

import com.shcherbakov.androidstudio_proekt_shcherbakov.ui.theme.AndroidStudio_Proekt_ShcherbakovTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AndroidStudio_Proekt_ShcherbakovTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MovieNavGraph(navController = navController)
                }
            }
        }
    }
}