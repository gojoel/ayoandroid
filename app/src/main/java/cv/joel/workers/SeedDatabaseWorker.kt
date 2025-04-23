package cv.joel.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cv.joel.ayoandroid.data.database.dao.LessonDao
import cv.joel.ayoandroid.data.database.dao.LessonUnitDao
import cv.joel.ayoandroid.data.database.dao.SectionDao
import cv.joel.ayoandroid.data.database.model.Lesson
import cv.joel.ayoandroid.data.database.model.LessonUnit
import cv.joel.ayoandroid.data.database.model.Section
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@HiltWorker
class SeedDatabaseWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sectionDao: SectionDao,
    private val lessonUnitDao: LessonUnitDao,
    private val lessonDao: LessonDao
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val sections = readContent<Section>(applicationContext, "sections.json")
            sectionDao.insertAll(sections)

            val units = readContent<LessonUnit>(applicationContext, "units.json")
            lessonUnitDao.insertAll(units)

            val lessons = readContent<Lesson>(applicationContext, "lessons.json")
            lessonDao.insertAll(lessons)

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    inline fun <reified T> readContent(context: Context, fileName: String): List<T> {
        val json = context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }

        return Json.decodeFromString<List<T>>(json)
    }


    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}