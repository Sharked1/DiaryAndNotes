package com.example.diaryandnotes.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.diaryandnotes.data.local.entity.DiaryEntity
import com.example.diaryandnotes.data.local.entity.ImageUriEntity
import com.example.diaryandnotes.data.local.entity.NoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [DiaryEntity::class, NoteEntity::class, ImageUriEntity::class], version = 1, exportSchema = false)
abstract class DiaryAndNotesDatabase: RoomDatabase() {

    abstract fun diaryDao(): DiaryDao
    abstract fun notesDao(): NotesDao

}