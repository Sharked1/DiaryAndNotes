package com.example.diaryandnotes.data.local.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "diary")
data class DiaryEntity(
    @field:PrimaryKey(true)
    val id: Int,

    val title: String,

    val description: String,

    val createdAt: String,
) :Parcelable

@Parcelize
@Entity(tableName = "image_uris")
data class ImageUriEntity(
    @PrimaryKey(true)
    val id: Int,
    val uri: String,
    val diaryId: Int
): Parcelable

data class DiaryAndImageUri(
    @Embedded
    val diary: DiaryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "diaryId",
        )
    val imageUri: List<ImageUriEntity>
)