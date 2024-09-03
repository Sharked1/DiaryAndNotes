package com.example.diaryandnotes.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diaryandnotes.data.local.entity.NoteEntity

@Dao
interface NotesDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getNote(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNote(id: Int)

}