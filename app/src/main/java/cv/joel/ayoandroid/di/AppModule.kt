package cv.joel.ayoandroid.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import cv.joel.ayoandroid.data.database.AppDatabase
import cv.joel.ayoandroid.data.database.dao.LessonDao
import cv.joel.ayoandroid.data.database.dao.LessonUnitDao
import cv.joel.ayoandroid.data.database.dao.SectionDao
import cv.joel.ayoandroid.data.repository.LessonStore
import cv.joel.ayoandroid.data.repository.LocalLessonStore
import cv.joel.ayoandroid.data.repository.LocalSectionStore
import cv.joel.ayoandroid.data.repository.LocalUnitLessonStore
import cv.joel.ayoandroid.data.repository.SectionStore
import cv.joel.ayoandroid.data.repository.UnitLessonStore
import cv.joel.ayoandroid.utilities.DATABASE_NAME
import cv.joel.workers.SeedDatabaseWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                        .build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            })
            .build()

    @Provides
    @Singleton
    fun provideLessonDao(
        database: AppDatabase
    ): LessonDao = database.lessonDao()

    @Provides
    @Singleton
    fun provideSectionDao(
        database: AppDatabase
    ): SectionDao = database.sectionDao()

    @Provides
    @Singleton
    fun provideLessonUnitDao(
        database: AppDatabase
    ): LessonUnitDao = database.unitDao()

    @Provides
    @Singleton
    fun provideLessonStore(
        lessonDao: LessonDao
    ): LessonStore = LocalLessonStore(lessonDao)

    @Provides
    @Singleton
    fun provideSectionStore(
        sectionDao: SectionDao
    ): SectionStore = LocalSectionStore(sectionDao)

    @Provides
    @Singleton
    fun provideUnitStore(
        unitLessonDao: LessonUnitDao
    ): UnitLessonStore = LocalUnitLessonStore(unitLessonDao)
}