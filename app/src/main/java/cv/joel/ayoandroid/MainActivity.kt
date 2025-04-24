package cv.joel.ayoandroid

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cv.joel.ayoandroid.ui.navigation.AyoNavHost
import cv.joel.ayoandroid.ui.theme.AyoAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.White.toArgb()))
        setContent {
            AyoAndroidTheme {
                AyoNavHost()
            }
        }
    }
}