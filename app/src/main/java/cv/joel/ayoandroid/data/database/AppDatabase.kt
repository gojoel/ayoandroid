package cv.joel.ayoandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cv.joel.ayoandroid.data.database.dao.LessonDao
import cv.joel.ayoandroid.data.database.dao.LessonUnitDao
import cv.joel.ayoandroid.data.database.dao.SectionDao
import cv.joel.ayoandroid.data.database.model.Lesson
import cv.joel.ayoandroid.data.database.model.LessonUnit
import cv.joel.ayoandroid.data.database.model.Section
import cv.joel.ayoandroid.data.database.model.UnitLessonsCrossRef

@Database(
    entities = [Section::class, LessonUnit::class, Lesson::class, UnitLessonsCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sectionDao(): SectionDao
    abstract fun unitDao(): LessonUnitDao
    abstract fun lessonDao(): LessonDao

}