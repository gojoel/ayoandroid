package cv.joel.ayoandroid.ui.lesson

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cv.joel.ayoandroid.R
import cv.joel.ayoandroid.ui.shared.AyoButton

@Composable
fun LessonCompleteView(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0B1622), Color.Black))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                stringResource(id = R.string.lesson_complete),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Image(
                painterResource(id = R.drawable.android_jetpack),
                contentDescription = stringResource(id = R.string.lesson_complete_img_desc),
                modifier = Modifier.fillMaxWidth(0.75f)
            )

            // TODO: Replace with actual XP and streak tracking
            Text("+100 XP", fontSize = 20.sp, color = Color.Green)
            Text("🔥 Streak: 5 Days", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f))

            AyoButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onContinue,
                text = stringResource(R.string.continue_label),
                enabled = true
            )
        }
    }
}
