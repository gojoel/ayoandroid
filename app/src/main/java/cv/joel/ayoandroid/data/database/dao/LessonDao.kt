package cv.joel.ayoandroid.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import cv.joel.ayoandroid.data.database.model.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LessonDao : BaseDao<Lesson> {

    @Query("SELECT * FROM lessons")
    abstract fun getAllLessons(): Flow<List<Lesson>>

    @Query("SELECT * FROM lessons WHERE lessonId = :id")
    abstract fun getLessonById(id: Long): Flow<Lesson>
}