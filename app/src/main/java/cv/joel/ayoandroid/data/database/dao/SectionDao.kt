package cv.joel.ayoandroid.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import cv.joel.ayoandroid.data.database.model.Section
import cv.joel.ayoandroid.data.database.model.SectionUnitLessons
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SectionDao : BaseDao<Section> {
    @Query("SELECT * FROM sections")
    abstract fun getSections(): Flow<List<Section>>

    @Transaction
    @Query("SELECT * FROM sections")
    abstract fun getSectionsWithUnitLessons(): Flow<List<SectionUnitLessons>>
}