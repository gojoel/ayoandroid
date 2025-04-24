package cv.joel.ayoandroid.data.database.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    tableName = "sections"
)
@Immutable
@Serializable
data class Section(
    @PrimaryKey @ColumnInfo(name = "sectionId") val id: Long,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "title") val title: String
)