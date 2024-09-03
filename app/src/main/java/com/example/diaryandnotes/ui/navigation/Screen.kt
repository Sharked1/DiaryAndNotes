package com.example.diaryandnotes.ui.navigation

sealed class Screen(val route: String) {
    object NoteList: Screen("note_list")
    object DiaryList: Screen("diary_screen")
    object DetailNotes: Screen("detail_notes?notesId={notesId}") {
        fun createRoute(notesId: String?): String {
            return if (notesId != null) {
                "detail_notes?notesId=$notesId"
            } else {
                "detail_notes"
            }
        }
    }
    object DetailDiary: Screen("detail_diary/{diaryId}") {
        fun createRoute(diaryId: Int) = "detail_diary/$diaryId"
    }
}