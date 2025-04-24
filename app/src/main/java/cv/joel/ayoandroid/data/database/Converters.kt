package cv.joel.ayoandroid.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { encodeDefaults = true; ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return json.encodeToString(ListSerializer(String.serializer()), value ?: emptyList())
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return json.decodeFromString(ListSerializer(String.serializer()), value)
    }

    @TypeConverter
    fun fromIntList(value: List<Int>?): String {
        return json.encodeToString(ListSerializer(Int.serializer()), value ?: emptyList())
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return json.decodeFromString(ListSerializer(Int.serializer()), value)
    }
}