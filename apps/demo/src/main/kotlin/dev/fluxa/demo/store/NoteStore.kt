package dev.fluxa.demo.store

import dev.fluxa.state.FluxaLogMiddleware
import dev.fluxa.state.storeOf

fun noteReducer(state: NoteState, action: NoteAction): NoteState = when (action) {
    is NoteAction.Add -> state.copy(notes = state.notes + action.note)
    is NoteAction.Update -> state.copy(
        notes = state.notes.map { if (it.id == action.note.id) action.note else it },
    )
    is NoteAction.Delete -> state.copy(
        notes = state.notes.filter { it.id != action.noteId },
    )
    is NoteAction.SetCategory -> state.copy(selectedCategory = action.category)
    is NoteAction.SetSearch -> state.copy(searchQuery = action.query)
    is NoteAction.SetSyncing -> state.copy(syncing = action.syncing)
    is NoteAction.LoadAll -> state.copy(notes = action.notes)
}

fun createNoteStore() = storeOf(
    initial = NoteState(),
    middleware = listOf(FluxaLogMiddleware("NoteStore")),
    reducer = ::noteReducer,
)

val sampleNotes = listOf(
    Note("1", "Welcome to Fluxa Notes", "This is a demo app built with the Fluxa framework.", NoteCategory.GENERAL),
    Note("2", "Architecture ideas", "Explore typed navigation and unidirectional state flow.", NoteCategory.IDEAS),
    Note("3", "Sprint planning", "Review the backlog and assign tasks for next sprint.", NoteCategory.WORK),
    Note("4", "Grocery list", "Milk, eggs, bread, coffee.", NoteCategory.PERSONAL),
    Note("5", "Fluxa themes", "Test Aurora and Aurora Dark themes across all screens.", NoteCategory.IDEAS),
)
