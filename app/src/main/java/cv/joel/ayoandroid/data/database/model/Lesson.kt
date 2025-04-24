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
    @PrimaryKey @ColumnInfo(name = "lessonId") val id: Long,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "unitId") val unitId: Long,
    @ColumnInfo(name = "completed") val completed: Boolean = false,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "objective") val objective: String,
    @ColumnInfo(name = "prompt") val prompt: String,
    @ColumnInfo(name = "codeTemplate") val codeTemplate: String,
    @ColumnInfo(name = "blanks") val blanks: Int,
    @ColumnInfo(name = "options") val options: List<String>,
    @ColumnInfo(name = "correctAnswers") val correctAnswers: List<String>,
    @ColumnInfo(name = "explanationCorrect") val explanationCorrect: String,
    @ColumnInfo(name = "explanationIncorrect") val explanationIncorrect: String
)
