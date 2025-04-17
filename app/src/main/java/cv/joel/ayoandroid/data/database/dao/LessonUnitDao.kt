package cv.joel.ayoandroid.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import cv.joel.ayoandroid.data.database.model.LessonUnit
import cv.joel.ayoandroid.data.database.model.UnitLessons
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LessonUnitDao : BaseDao<LessonUnit> {
    @Query("SELECT * FROM units")
    abstract fun getUnits(): Flow<List<LessonUnit>>

    @Transaction
    @Query("SELECT * FROM units")
    abstract fun getUnitsWithLessons(): Flow<List<UnitLessons>>
}