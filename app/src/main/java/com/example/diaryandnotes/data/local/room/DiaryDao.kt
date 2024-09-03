package com.example.diaryandnotes.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diaryandnotes.data.local.entity.DiaryEntity

@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary")
    suspend fun getDiaries(): List<DiaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: List<DiaryEntity>)
}