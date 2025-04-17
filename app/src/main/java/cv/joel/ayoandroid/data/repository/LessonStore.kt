package cv.joel.ayoandroid.data.repository

import cv.joel.ayoandroid.data.database.dao.LessonDao
import cv.joel.ayoandroid.data.database.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonStore {
    fun getLessons(): Flow<List<Lesson>>
}

class LocalLessonStore(
    private val lessonDao: LessonDao
) : LessonStore {
    override fun getLessons(): Flow<List<Lesson>> = lessonDao.getAllLessons()
}