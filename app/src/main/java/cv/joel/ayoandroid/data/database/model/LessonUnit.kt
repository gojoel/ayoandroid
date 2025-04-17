package cv.joel.ayoandroid.data.database.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "units",
    foreignKeys = [
        ForeignKey(
            entity = Section::class,
            parentColumns = ["sectionId"],
            childColumns = ["sectionId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Immutable
data class LessonUnit(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "unitId") val id: Long = 0,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "summary") val summary: String,
    @ColumnInfo(name = "sectionId") val sectionId: String
)