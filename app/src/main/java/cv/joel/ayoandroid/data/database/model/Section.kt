package cv.joel.ayoandroid.data.database.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "sections"
)
@Immutable
data class Section(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "sectionId") val id: Long = 0,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "title") val title: String
)