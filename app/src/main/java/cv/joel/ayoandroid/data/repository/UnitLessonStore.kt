package cv.joel.ayoandroid.data.repository

import cv.joel.ayoandroid.data.database.dao.LessonUnitDao
import cv.joel.ayoandroid.data.database.model.LessonUnit
import kotlinx.coroutines.flow.Flow

interface UnitLessonStore {
    fun getUnits(): Flow<List<LessonUnit>>
}

class LocalUnitLessonStore(
    private val lessonUnitDao: LessonUnitDao
) : UnitLessonStore {
    override fun getUnits(): Flow<List<LessonUnit>> =
        lessonUnitDao.getUnits()
}