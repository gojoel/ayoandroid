package cv.joel.ayoandroid.di

import android.content.Context
import androidx.room.Room
import cv.joel.ayoandroid.data.database.AppDatabase
import cv.joel.ayoandroid.data.database.dao.LessonDao
import cv.joel.ayoandroid.data.repository.LessonStore
import cv.joel.ayoandroid.data.repository.LocalLessonStore
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
        Room.databaseBuilder(context, AppDatabase::class.java, "ayo.db").build()

    @Provides
    @Singleton
    fun provideLessonDao(
        database: AppDatabase
    ): LessonDao = database.lessonDao()

    @Provides
    @Singleton
    fun provideLessonStore(
        lessonDao: LessonDao
    ): LessonStore = LocalLessonStore(lessonDao)

}