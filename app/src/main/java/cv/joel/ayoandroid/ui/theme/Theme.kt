package cv.joel.ayoandroid.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val AndroidGreen = Color(0xFF3DDC84)
val DeepSpaceBlue = Color(0xFF0B1622)
val TerminalCyan = Color(0xFF00E0FF)
val ErrorRed = Color(0xFFEF5350)

private val LightColors = lightColorScheme(
    primary = AndroidGreen,
    secondary = TerminalCyan,
    background = DeepSpaceBlue,
    surface = DeepSpaceBlue,
    onPrimary = Color.Black,
    onSurface = Color.White,
    error = ErrorRed
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF3DDC84),
    secondary = Color(0xFF00E0FF),
    background = Color(0xFF0B1622),
    surface = Color(0xFF112233),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color(0xFFEF5350)
)

@Composable
fun AyoAndroidTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColors,
        shapes = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(8.dp),
            large = RoundedCornerShape(12.dp)
        ),
        content = content
    )
}