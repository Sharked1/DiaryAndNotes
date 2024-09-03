package com.example.diaryandnotes.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val createdAt: String
): Parcelable