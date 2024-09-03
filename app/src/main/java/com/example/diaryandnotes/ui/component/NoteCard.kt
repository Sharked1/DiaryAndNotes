package com.example.diaryandnotes.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diaryandnotes.data.local.entity.NoteEntity
import com.example.diaryandnotes.helper.unixToDateTimePair
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: NoteEntity,
    isMarked: Boolean,
    onClick: (Int) -> Unit,
    onLongPress: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pairDateTime = unixToDateTimePair(note.createdAt.toLong())
    var isPressed by remember { mutableStateOf(false) }
    val border by animateDpAsState(
        targetValue = if (isMarked) 2.dp else 0.dp,
        animationSpec = tween(durationMillis = 300),
    )
//    val scale by animateFloatAsState(
//        targetValue = if (isMarked) 0.5f else 1f,
//        animationSpec = tween(3000)
//    )
    val color by animateColorAsState(
        targetValue = if (isMarked) Color.Gray else Color.LightGray,
        animationSpec = tween(durationMillis = 300)
    )

    val offset by animateDpAsState(
        targetValue = 100.dp
    )
    Box(
        modifier = Modifier.background(Color.Green).padding(4.dp)
    ){
        Card(
            colors = CardDefaults.cardColors(),
            modifier = modifier
                .padding(4.dp)
                .border(border, Color.Magenta, RoundedCornerShape(12.dp))
                .fillMaxSize()
                .combinedClickable(
                    onClick = { onClick(note.id) },
                    onLongClick = { onLongPress(note.id) },
                )
        ) {
            Text(text = note.id.toString())
            Text(
                text = note.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
            Text(text = pairDateTime.first)
            Text(text = pairDateTime.second)
        }
    }
}