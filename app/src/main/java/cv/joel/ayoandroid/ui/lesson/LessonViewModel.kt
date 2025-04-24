package cv.joel.ayoandroid.ui.lesson

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

    private val _lesson = MutableStateFlow<Lesson?>(null)
    val lesson: StateFlow<Lesson?> = _lesson

    fun loadLesson(lessonId: Long) {
        viewModelScope.launch {
            lessonStore.getLessonById(lessonId)
                .catch { error -> error.printStackTrace() }
                .collect { lessonContent ->
                    _lesson.update { lessonContent }
                }
        }
    }
}
