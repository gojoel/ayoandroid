package cv.joel.ayoandroid.ui.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cv.joel.ayoandroid.data.database.model.Lesson
import cv.joel.ayoandroid.data.database.model.LessonUnit
import cv.joel.ayoandroid.data.database.model.Section
import cv.joel.ayoandroid.data.repository.SectionStore
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
class HomeViewModel @Inject constructor(
    private val sectionStore: SectionStore
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())

    private val sectionData = sectionStore.getSectionData()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val state: StateFlow<HomeUiState>
        get() = _state

    init {
        viewModelScope.launch {

            sectionData.collectLatest {

                val sections = it.map { it.section }.toPersistentList()
                val lessons =
                    it.flatMap { it.unitLessons }.flatMap { it.lessons }.toPersistentList()
                val currentLessonIndex = lessons.indexOfFirst { !it.completed }

                _state.value = HomeUiState(
                    loading = false,
                    sections = sections,
                    lessons = lessons,
                    currentLessonIndex = currentLessonIndex
                )
            }
        }
    }

    @Immutable
    data class HomeUiState(
        val loading: Boolean = true,
        val error: Boolean = false,
        val currentLessonIndex: Int = 0,
        val sections: PersistentList<Section> = persistentListOf(),
        val units: PersistentList<LessonUnit> = persistentListOf(),
        val lessons: PersistentList<Lesson> = persistentListOf()
    )
}