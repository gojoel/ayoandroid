package cv.joel.ayoandroid.data.database.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = Section::class,
            parentColumns = ["sectionId"],
            childColumns = ["sectionId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LessonUnit::class,
            parentColumns = ["unitId"],
            childColumns = ["unitId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Immutable
@Serializable
data class Lesson(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "lessonId") val id: Long = 0,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "unitId") val unitId: String,
    @ColumnInfo(name = "sectionId") val sectionId: String,
    @ColumnInfo(name = "completed") val completed: Boolean = false
)
