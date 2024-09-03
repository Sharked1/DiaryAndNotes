package com.example.diaryandnotes.data

import com.example.diaryandnotes.data.local.entity.NoteEntity
import com.example.diaryandnotes.data.local.room.DiaryDao
import com.example.diaryandnotes.data.local.room.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class Repository  @Inject constructor(private val diaryDao: DiaryDao, private val notesDao: NotesDao) {
    suspend fun getAllNotes(): Flow<List<NoteEntity>> {
        return flowOf(notesDao.getNote())
    }

    suspend fun addNotes(note: NoteEntity = NoteEntity(title = "", description = "", createdAt = (System.currentTimeMillis()/1000).toString())) {
        notesDao.insertNotes(note)
    }
    suspend fun deleteNote(id: Int) {
        notesDao.deleteNote(id)
    }
}