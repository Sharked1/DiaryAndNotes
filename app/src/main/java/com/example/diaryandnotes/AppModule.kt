package com.example.diaryandnotes

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.diaryandnotes.data.Repository
import com.example.diaryandnotes.data.local.room.DiaryAndNotesDatabase
import com.example.diaryandnotes.data.local.room.DiaryDao
import com.example.diaryandnotes.data.local.room.InitialDataSource
import com.example.diaryandnotes.data.local.room.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRepository(
        diaryDao: DiaryDao,
        notesDao: NotesDao
    ): Repository {
        return Repository(diaryDao, notesDao)
    }

    @Provides
    fun provideDiaryDao(database: DiaryAndNotesDatabase): DiaryDao {
        return database.diaryDao()
    }

    @Provides
    fun provideNotesDao(database: DiaryAndNotesDatabase): NotesDao {
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DiaryAndNotesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DiaryAndNotesDatabase::class.java,
            "diary_and_notes_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val coroutineScope = CoroutineScope(SupervisorJob())
                    coroutineScope.launch {
                        val diaryDao = provideDatabase(context).diaryDao()
                        val notesDao = provideDatabase(context).notesDao()
                        diaryDao.insertDiary(InitialDataSource.getInitialDiary())
                        InitialDataSource.getInitialNotes().forEach {
                            notesDao.insertNotes(it)
                        }
                    }
                }
            })
            .build()
    }
}