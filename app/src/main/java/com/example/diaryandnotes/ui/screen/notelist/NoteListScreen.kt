package com.example.diaryandnotes.ui.screen.notelist

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diaryandnotes.R
import com.example.diaryandnotes.data.local.entity.NoteEntity
import com.example.diaryandnotes.helper.unixToDateTimePair
import com.example.diaryandnotes.ui.component.NoteCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteListViewModel = hiltViewModel(),
    onNotesClicked: (String) -> Unit,
    onNewNotesClicked: () -> Unit,
) {
    val context = LocalContext.current
    val notes by viewModel.notes.collectAsState()
    var coroutineScope = rememberCoroutineScope()
    val markedNotes = remember { mutableStateListOf<Int>() }
    val isBlockMode by remember { derivedStateOf { markedNotes.size != 0 } }
    var showAlertDialog by remember { mutableStateOf(false) }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            confirmButton = {
                FilledTonalButton(
                    onClick = { 
                        markedNotes.forEach { 
                            viewModel.deleteNote(it) 
                        }
                        markedNotes.clear()
                        viewModel.getAllNotes()
                        showAlertDialog = false }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text(text = "No")
                }
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_dangerous_24),
                    contentDescription = "",
                    modifier = Modifier.size(80.dp)
                )
            },
            title = {
                Text(
                    text = "Are you sure to delete this notes?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Left
                )
            },
            text = {
                Text(text = "This action cannot be undone")
            },
            modifier = Modifier
        )
    }

    Column(
        modifier
            .fillMaxSize()
    ) {
        BackHandler(isBlockMode) {
            markedNotes.clear()
        }
        TopAppBar(
            title = { Text(text = "Note List") },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        onNewNotesClicked()
                        Toast.makeText(context, "Test navigation icon", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            actions = {
                if (isBlockMode) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = modifier.clickable {
                            showAlertDialog = true
//                            markedNotes.forEach {
//                                viewModel.deleteNote(it)
//                            }
//                            markedNotes.clear()
//                            viewModel.getAllNotes()
                        }
                    )
                }
                else {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Test action 1 icon", Toast.LENGTH_SHORT).show()
                        }

                    )
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "Test action 2 icon", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        )

        Button(onClick = { viewModel.getAllNotes() }) {
            Text(text = "Refresh")
        }
        Button(onClick = { viewModel.addNotes() }) {
            Text(text = "Add dummy notes")
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            itemsIndexed(
                items = notes,
                key = { _, note -> note.id }
            ) { index, note ->
                var isVisible by remember { mutableStateOf(true) }
                val isMarked by remember { derivedStateOf { markedNotes.contains(note.id) } }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = modifier.animateItemPlacement()
                    ) {
                    NoteCard(
                        note = note,
                        isMarked = isMarked,
                        onLongPress = {
                            markedNotes.add(it)
                            Toast.makeText(context, "Long press", Toast.LENGTH_SHORT).show()
                        },
                        onClick = {
                            if (isBlockMode) {
                                if (isMarked) {
                                    markedNotes.remove(it)
                                } else {
                                    markedNotes.add(it)
                                }
                            } else {
                                onNotesClicked(it.toString())
                            }
                        }

                    )

                }

            }
        }
    }
}

