package cv.joel.ayoandroid.ui.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cv.joel.ayoandroid.R
import cv.joel.ayoandroid.data.database.model.LessonType

@Composable
fun LessonScreen(
    lessonId: Long,
    onBack: () -> Unit,
) {
    val viewModel = hiltViewModel<LessonViewModel>()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(lessonId) {
        viewModel.loadLesson(lessonId)
    }

    if (uiState.complete) {
        LessonCompleteView(onContinue = onBack)
        return
    }

    LessonContent(uiState = uiState, onLessonAction = {
        when (it) {
            is LessonAction.OnNavigateUp -> onBack()
            else -> viewModel.onLessonAction(it)
        }
    })
}

@Composable
fun LessonContent(uiState: LessonViewModel.LessonUiState, onLessonAction: (LessonAction) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF112233), Color(0xFF0B1622))
                )
            )
    ) {
        IconButton(
            onClick = { onLessonAction.invoke(LessonAction.OnNavigateUp) },
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close_lesson_desc),
                tint = Color.White
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(vertical = 48.dp, horizontal = 16.dp)
        ) {

            uiState.lesson?.let {
                Text(
                    text = it.title.uppercase(),
                    color = Color.Cyan,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = it.objective,
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )

                Text(
                    text = it.prompt,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                when (it.type) {
                    LessonType.MULTIPLE_CHOICE -> MultipleChoiceLessonView(
                        lesson = it,
                        onComplete = {
                            onLessonAction.invoke(LessonAction.OnLessonCompleted)
                        }
                    )

                    LessonType.FILL_IN_THE_BLANK -> {
                        CodeFillView(
                            lesson = it,
                            onComplete = {
                                onLessonAction.invoke(LessonAction.OnLessonCompleted)
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}