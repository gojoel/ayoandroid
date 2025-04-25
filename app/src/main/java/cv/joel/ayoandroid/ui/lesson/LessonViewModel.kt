package cv.joel.ayoandroid.ui.lesson

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cv.joel.ayoandroid.data.database.model.Lesson
import cv.joel.ayoandroid.data.repository.LessonStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val lessonStore: LessonStore
) : ViewModel() {

    private val _state = MutableStateFlow(LessonUiState())
    val state: StateFlow<LessonUiState>
        get() = _state

    fun loadLesson(lessonId: Long) {
        viewModelScope.launch {
            lessonStore.getLessonById(lessonId)
                .catch { error -> error.printStackTrace() }
                .collect { lesson ->
                    _state.update { it.copy(lesson = lesson) }
                }
        }
    }

    fun onLessonAction(lessonAction: LessonAction) {
        when (lessonAction) {
            is LessonAction.OnLessonCompleted -> {
                onLessonComplete()
            }

            is LessonAction.OnNavigateUp -> {}
        }
    }

    private fun onLessonComplete() {
        _state.update { it.copy(complete = true) }
    }

    @Immutable
    data class LessonUiState(
        val lesson: Lesson? = null,
        val complete: Boolean = false
    )
}
