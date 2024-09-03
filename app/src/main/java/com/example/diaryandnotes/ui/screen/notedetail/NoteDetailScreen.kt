package com.example.diaryandnotes.ui.screen.notedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: String? = null,
    onClicked: () -> Unit,
) {
    Column {
        TopAppBar(title = { Text(text = "Detail Notes") })
        Text(text = noteId.toString(), modifier = Modifier.clickable {
            onClicked()
        })
    }

}