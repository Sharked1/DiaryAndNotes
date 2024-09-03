package com.example.diaryandnotes

import android.app.Application
import com.example.diaryandnotes.data.local.room.DiaryAndNotesDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MyApplication: Application() {

}