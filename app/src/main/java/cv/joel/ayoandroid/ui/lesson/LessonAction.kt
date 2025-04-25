package cv.joel.ayoandroid.ui.lesson

sealed interface LessonAction {
    data object OnLessonCompleted : LessonAction
    data object OnNavigateUp : LessonAction
}