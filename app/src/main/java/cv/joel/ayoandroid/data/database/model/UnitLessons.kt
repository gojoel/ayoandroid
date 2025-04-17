package cv.joel.ayoandroid.data.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UnitLessons(
    @Embedded val lessonUnit: LessonUnit,
    @Relation(
        parentColumn = "unitId",
        entityColumn = "lessonId",
        associateBy = Junction(UnitLessonsCrossRef::class)
    )
    val lessons: List<Lesson>
)
