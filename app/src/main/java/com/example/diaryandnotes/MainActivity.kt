package com.example.diaryandnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diaryandnotes.ui.animation.Transition
import com.example.diaryandnotes.ui.navigation.Screen
import com.example.diaryandnotes.ui.screen.notedetail.NoteDetailScreen
import com.example.diaryandnotes.ui.screen.notelist.NoteListScreen
import com.example.diaryandnotes.ui.theme.DiaryAndNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiaryAndNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = { 
                                    navController.navigate(Screen.DiaryList.route)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "")
                            }
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.NoteList.route,
                            enterTransition = { EnterTransition.None },
                            exitTransition = { ExitTransition.None },
                            popEnterTransition = { EnterTransition.None },
                            popExitTransition = { ExitTransition.None },
                            modifier = Modifier.padding(it)
                        ) {
                            composable(
                                route = Screen.NoteList.route,
                                enterTransition = {
                                    Transition.stdFadeIn()
                                },
                                exitTransition = {
                                    Transition.stdFadeOut()
                                },
                            ) {
                                NoteListScreen(
                                    onNotesClicked = {
                                        navController.navigate(Screen.DetailNotes.createRoute(it))
                                    },
                                    onNewNotesClicked = {
                                        navController.navigate(Screen.DetailNotes.route)
                                    },
                                )
                            }
                            composable(
                                route = Screen.DetailNotes.route,
                                arguments = listOf(navArgument("notesId") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }),
                                enterTransition = {
                                    slideIntoContainer(
                                        towards =  AnimatedContentTransitionScope.SlideDirection.Up,
                                        animationSpec = tween(
                                            durationMillis = 300,
                                            easing = EaseInOutSine
                                        ),
                                    )
                                },
                                exitTransition = {
                                    slideOutOfContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                        animationSpec = tween(
                                            durationMillis = 300,
                                            easing = EaseInOutSine
                                        ),
                                    )
                                },
                            )
                            {
                                NoteDetailScreen(noteId = it.arguments?.getString("notesId"), onClicked = {
                                    navController.navigate(Screen.DiaryList.route)
                                })
                            }
                            composable(
                                route = Screen.DiaryList.route,
                                enterTransition = {
                                    Transition.stdFadeIn() + slideInHorizontally(
                                        animationSpec = tween(300, easing = EaseIn),
                                        initialOffsetX = { it }
                                    )
                                },
                                exitTransition = {
                                    Transition.stdFadeOut() + slideOutHorizontally(
                                        animationSpec = tween(300, easing = EaseIn),
                                        targetOffsetX = { it }
                                    )
                                }
                            ) {
                                Text("Diary list screen")
                            }
                        }
                    }

                }
            }
        }
    }
}