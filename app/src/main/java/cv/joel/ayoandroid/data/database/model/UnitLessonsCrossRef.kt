package cv.joel.ayoandroid.data.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["unitId", "lessonId"])
data class UnitLessonsCrossRef(
    val unitId: Long,
    val lessonId: Long
)