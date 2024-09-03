package com.example.diaryandnotes.ui.screen.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryandnotes.data.Repository
import com.example.diaryandnotes.data.local.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _notes: MutableStateFlow<List<NoteEntity>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    init {
        if (notes.value.isEmpty()) {
            getAllNotes()
        }
    }
    fun getAllNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    fun addNotes() {
        viewModelScope.launch {
            repository.addNotes()
            getAllNotes()
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id)
            repository.getAllNotes().collect {
                _notes.value = it
            }
        }
    }
}