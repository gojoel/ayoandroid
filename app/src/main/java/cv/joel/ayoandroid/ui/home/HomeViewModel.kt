package cv.joel.ayoandroid.ui.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cv.joel.ayoandroid.data.database.model.Lesson
import cv.joel.ayoandroid.data.repository.LessonStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val lessonStore: LessonStore) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())

    private val lessons = lessonStore.getLessons()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val state: StateFlow<HomeUiState>
        get() = _state

    init {
        viewModelScope.launch {
            lessons.collectLatest {
                _state.value = HomeUiState(loading = false, lessons = it.toPersistentList())
            }
        }
    }

    @Immutable
    data class HomeUiState(
        val loading: Boolean = true,
        val error: Boolean = false,
        val lessons: PersistentList<Lesson> = persistentListOf()
    )
}