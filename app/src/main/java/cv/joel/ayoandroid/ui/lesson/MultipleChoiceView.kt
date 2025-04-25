package cv.joel.ayoandroid.ui.lesson

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cv.joel.ayoandroid.data.database.model.Lesson

@Composable
fun MultipleChoiceLessonView(lesson: Lesson, onComplete: () -> Unit) {
    var selectedAnswer by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF1E2A38),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = lesson.codeTemplate,
                color = Color.Cyan,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        lesson.options.forEachIndexed { index, option ->
            val isSelected = option == selectedAnswer
            val borderColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f)

            OutlinedButton(
                onClick = {
                    selectedAnswer = option
                    if (lesson.correctAnswers.contains(selectedAnswer)) {
                        onComplete()
                    }
                },
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(text = option)
            }
        }

        if (selectedAnswer.isNotEmpty() && !lesson.correctAnswers.contains(selectedAnswer)) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "❌ Try again!",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
