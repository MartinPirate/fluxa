package dev.fluxa.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.fluxa.compose.FluxaRenderContext
import dev.fluxa.compose.FluxaTheme
import dev.fluxa.compose.RenderFluxaNode
import dev.fluxa.demo.data.NoteApi
import dev.fluxa.demo.screens.createNoteScreen
import dev.fluxa.demo.screens.homeScreen
import dev.fluxa.demo.screens.noteDetailScreen
import dev.fluxa.demo.screens.settingsScreen
import dev.fluxa.demo.store.NoteAction
import dev.fluxa.demo.store.createNoteStore
import dev.fluxa.demo.store.sampleNotes
import dev.fluxa.demo.work.SyncTask
import dev.fluxa.effect.FluxaOnceEffect
import dev.fluxa.effect.FluxaPollingEffect
import dev.fluxa.effect.collectAsState
import dev.fluxa.nav.FluxaBackStack
import dev.fluxa.nav.FluxaRouter
import dev.fluxa.style.FluxaThemeMode
import dev.fluxa.style.FluxaThemes
import dev.fluxa.store.fluxaPreferences
import dev.fluxa.work.fluxaScheduler
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scheduler = fluxaScheduler()
        scheduler.runPeriodic<SyncTask>(
            tag = "note-sync",
            intervalMinutes = 15,
        )

        setContent {
            DemoApp()
        }
    }
}

@Composable
private fun DemoApp() {
    val noteStore = remember { createNoteStore() }
    val noteApi = remember { NoteApi() }
    val backStack = remember { FluxaBackStack(Routes.Home) }
    var useDark by remember { mutableStateOf(false) }

    val theme = if (useDark) FluxaThemes.AuroraDark else FluxaThemes.Aurora
    val storeState by noteStore.collectAsState()

    // Load notes on first composition
    FluxaOnceEffect(Unit) {
        val result = noteApi.fetchNotes()
        val notes = result.dataOrNull()
        if (notes != null) {
            noteStore.dispatch(NoteAction.LoadAll(notes))
        }
    }

    // Simulate periodic sync
    FluxaPollingEffect(intervalMs = 30_000, Unit) {
        noteStore.dispatch(NoteAction.SetSyncing(true))
        noteApi.syncNotes()
        noteStore.dispatch(NoteAction.SetSyncing(false))
    }

    FluxaTheme(theme = theme) {
        FluxaRouter(backStack = backStack) { route ->
            val tree = when (route) {
                is Routes.Home -> homeScreen(
                    state = storeState,
                    theme = theme,
                    onNoteClick = { id -> backStack.push(Routes.NoteDetail(id)) },
                    onNewNote = { backStack.push(Routes.CreateNote) },
                )
                is Routes.NoteDetail -> {
                    val note = storeState.notes.find { it.id == route.noteId }
                    noteDetailScreen(
                        note = note,
                        theme = theme,
                        onDelete = { id ->
                            noteStore.dispatch(NoteAction.Delete(id))
                            backStack.pop()
                        },
                        onBack = { backStack.pop() },
                    )
                }
                is Routes.CreateNote -> createNoteScreen(theme)
                is Routes.Settings -> settingsScreen(
                    isDark = useDark,
                    theme = theme,
                    onToggleDark = { useDark = it },
                    onClearCache = { noteApi.invalidateCache() },
                )
                else -> homeScreen(storeState, theme)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                RenderFluxaNode(
                    node = tree,
                    context = FluxaRenderContext(),
                )
            }
        }
    }
}
