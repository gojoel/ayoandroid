package cv.joel.ayoandroid.data.repository

import cv.joel.ayoandroid.data.database.dao.SectionDao
import cv.joel.ayoandroid.data.database.model.SectionUnitLessons
import kotlinx.coroutines.flow.Flow

interface SectionStore {
    fun getSectionData(): Flow<List<SectionUnitLessons>>
}

class LocalSectionStore(
    private val sectionDao: SectionDao
) : SectionStore {
    override fun getSectionData(): Flow<List<SectionUnitLessons>> =
        sectionDao.getSectionsWithUnitLessons()
}