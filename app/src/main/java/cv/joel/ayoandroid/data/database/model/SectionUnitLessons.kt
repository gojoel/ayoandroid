package cv.joel.ayoandroid.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class SectionUnitLessons(
    @Embedded val section: Section,
    @Relation(
        entity = LessonUnit::class,
        parentColumn = "sectionId",
        entityColumn = "sectionId"
    )
    val unitLessons: List<UnitLessons>
)