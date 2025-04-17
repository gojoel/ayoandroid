package cv.joel.ayoandroid.ui.navigation

object NavRoutes {
    const val HOME = "home"
    const val LESSON = "lesson/{lessonId}"
    fun lesson(lessonId: String) = "lesson/$lessonId"
}
