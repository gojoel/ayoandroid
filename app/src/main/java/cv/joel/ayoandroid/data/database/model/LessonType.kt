package cv.joel.ayoandroid.data.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LessonType {
    @SerialName("multiple_choice")
    MULTIPLE_CHOICE,

    @SerialName("fill_in_the_blank")
    FILL_IN_THE_BLANK,

    @SerialName("debug_the_code")
    DEBUG_THE_CODE,

    @SerialName("drag_to_order")
    DRAG_TO_ORDER,

    @SerialName("flashcard")
    FLASHCARD;

    companion object {
        fun fromString(value: String): LessonType =
            entries.find { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown lesson type: $value")
    }
}
