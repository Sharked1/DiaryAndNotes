package com.example.diaryandnotes.data.local.room

import com.example.diaryandnotes.data.local.entity.DiaryEntity
import com.example.diaryandnotes.data.local.entity.NoteEntity

object InitialDataSource {
    fun getInitialDiary(): List<DiaryEntity> {
        return listOf(
            DiaryEntity(
                1, "Go to Mall",
                "Today I go to mall with my mother. we bought many things like food supply, cleaner, etc",
                "1722273433",
            ),
            DiaryEntity(
                2,
                "Learning French",
                "Today I am decided to learn some french and i have memorized numbers from 1-10",
                "1722273499",
            ),
            DiaryEntity(
                3,
                "Meet a New Friend at School",
                "My class got a new student that named Richard. He seems like a quiet kid. but we don't really know since its his first day at school. Maybe he is just shy.",
                "1722279999",
                )
        )
    }

    fun getInitialNotes(): List<NoteEntity> {
        return listOf(
            NoteEntity(
                1,
                "Learning Material Links",
                "www.youtube.com\nwww.facebook.com\nwww.dicoding.com",
                "1722273433"
            ),
            NoteEntity(
                2,
                "Item Wishlist",
                "1. Gaming Mouse\n2. Powerbank 10000mah\n3. Gibson Guitar\n4. Sling bag\n5. Chili sauce\n6. Arduino uno",
                "1722273499"
            ),
            NoteEntity(
                3,
                "Bill to pay",
                "Electricity = 200.000\nWifi = 350.000\nFood = 500.000",
                "1722279999"
            )
        )
    }
}