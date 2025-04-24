package cv.joel.ayoandroid.ui.lesson

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CodeFillView(
    template: String,
    blanks: Int,
    options: List<String>,
    correctAnswers: List<String>,
    explanationCorrect: String,
    explanationIncorrect: String
) {
    val userAnswers = remember { mutableStateListOf<String>() }
    val answerMap = remember { mutableStateMapOf<Int, String>() }
    val availableOptions = remember { mutableStateListOf<String>().apply { addAll(options) } }
    val showFeedback = remember { mutableStateOf(false) }
    val isCorrect = remember { mutableStateOf(false) }

    if (userAnswers.size < blanks) {
        LaunchedEffect(Unit) {
            userAnswers.clear()
            userAnswers.addAll(List(blanks) { "" })
        }
        return
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CodeBlock(
            template = template,
            blanks = blanks,
            userAnswers = userAnswers,
            answerMap = answerMap,
            availableOptions = availableOptions,
            onClear = { showFeedback.value = false }
        )

        AnswerOptions(
            options = options,
            availableOptions = availableOptions,
            onOptionSelected = { selected ->
                val firstEmpty = userAnswers.indexOfFirst { it.isBlank() }
                if (firstEmpty != -1) {
                    userAnswers[firstEmpty] = selected
                    answerMap[firstEmpty] = selected
                    availableOptions.remove(selected)
                }
            }
        )

        Button(
            onClick = {
                val trimmed = userAnswers.map { it.trim() }
                isCorrect.value = trimmed == correctAnswers.map { it.trim() }
                showFeedback.value = true
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFF00BCD4).copy(alpha = 0.4f), // visible but dimmed
                disabledContentColor = Color.White.copy(alpha = 0.6f)
            ),
            enabled = userAnswers.any { it.isNotBlank() }
        ) {
            Text("Submit")
        }

        if (showFeedback.value) {
            FeedbackView(
                isCorrect = isCorrect.value,
                explanationCorrect = explanationCorrect,
                explanationIncorrect = explanationIncorrect
            )
        }
    }
}


@Composable
fun CodeBlock(
    template: String,
    blanks: Int,
    userAnswers: SnapshotStateList<String>,
    answerMap: MutableMap<Int, String>,
    availableOptions: SnapshotStateList<String>,
    onClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E3C5D), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        var blankIndex = 0
        val lines = template.split("\n")
        lines.forEach { line ->
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val parts = line.split("____")
                parts.forEachIndexed { i, part ->
                    Text(
                        text = part,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    if (blankIndex < blanks && i < parts.size - 1) {
                        val filled = userAnswers[blankIndex]
                        BlankSlot(
                            index = blankIndex,
                            filledText = filled,
                            onClear = { index ->
                                userAnswers[index] = ""
                                answerMap[index]?.let {
                                    availableOptions.add(it)
                                    answerMap.remove(index)
                                }
                                onClear()
                            }
                        )

                        blankIndex++
                    }
                }
            }
        }
    }
}

@Composable
fun BlankSlot(
    index: Int,
    filledText: String,
    onClear: (Int) -> Unit
) {
    val isFilled = filledText.isNotBlank()

    val animatedScale by animateFloatAsState(
        targetValue = if (isFilled) 1.1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "BlankSlotScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isFilled) Color.Cyan else Color.LightGray,
        animationSpec = tween(durationMillis = 300),
        label = "BlankSlotColor"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .clickable(enabled = isFilled) {
                onClear(index)
            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = if (isFilled) filledText else "____",
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            fontWeight = if (isFilled) FontWeight.Bold else FontWeight.Normal,
            color = if (isFilled) Color.Black else Color.DarkGray
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnswerOptions(
    availableOptions: List<String>,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        options.sorted().forEach { option ->
            if (availableOptions.contains(option)) {
                Button(
                    onClick = { onOptionSelected(option) },
                    modifier = modifier
                        .height(48.dp)
                        .defaultMinSize(minWidth = 100.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.3f)
                    )
                ) {
                    Text(option, fontSize = 14.sp, textAlign = TextAlign.Center)
                }
            } else {
                UsedOptionPlaceholder(text = option)
            }
        }
    }
}

@Composable
fun UsedOptionPlaceholder(text: String) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .defaultMinSize(minWidth = 100.dp)
            .border(1.dp, Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp))
            .background(Color.Transparent, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.4f),
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun FeedbackView(
    isCorrect: Boolean,
    explanationCorrect: String,
    explanationIncorrect: String
) {
    Text(
        text = if (isCorrect) explanationCorrect else explanationIncorrect,
        color = if (isCorrect) Color.Green else Color.Red,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp)
    )
}
