package io.github.codewithtamim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.codewithtamim.ui.screen.TuitionScreen
import io.github.codewithtamim.ui.theme.UIUCalculatorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UIUCalculatorTheme {
                Scaffold { innerPadding ->
                    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
                        TuitionScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}