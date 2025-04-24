package cv.joel.ayoandroid.data.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class UnitLessons(
    @Embedded val lessonUnit: LessonUnit,
    @Relation(
        entity = Lesson::class,
        parentColumn = "unitId",
        entityColumn = "unitId",
    )
    val lessons: List<Lesson>
)
