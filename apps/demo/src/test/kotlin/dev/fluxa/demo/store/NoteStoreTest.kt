package dev.fluxa.demo.store

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNull

class NoteStoreTest {

    private fun store(vararg notes: Note) = createNoteStore().also {
        if (notes.isNotEmpty()) {
            it.dispatch(NoteAction.LoadAll(notes.toList()))
        }
    }

    private val testNote = Note("1", "Test", "Body", NoteCategory.GENERAL)
    private val workNote = Note("2", "Work", "Task", NoteCategory.WORK)
    private val ideaNote = Note("3", "Idea", "Concept", NoteCategory.IDEAS)

    @Test
    fun `initial state is empty`() {
        val s = store()
        assertEquals(0, s.current.notes.size)
        assertNull(s.current.selectedCategory)
        assertEquals("", s.current.searchQuery)
    }

    @Test
    fun `add note`() {
        val s = store()
        s.dispatch(NoteAction.Add(testNote))
        assertEquals(1, s.current.notes.size)
        assertEquals("Test", s.current.notes[0].title)
    }

    @Test
    fun `load all replaces notes`() {
        val s = store()
        s.dispatch(NoteAction.LoadAll(listOf(testNote, workNote)))
        assertEquals(2, s.current.notes.size)
    }

    @Test
    fun `update note`() {
        val s = store(testNote)
        val updated = testNote.copy(title = "Updated")
        s.dispatch(NoteAction.Update(updated))
        assertEquals("Updated", s.current.notes[0].title)
    }

    @Test
    fun `delete note`() {
        val s = store(testNote, workNote)
        s.dispatch(NoteAction.Delete("1"))
        assertEquals(1, s.current.notes.size)
        assertEquals("2", s.current.notes[0].id)
    }

    @Test
    fun `filter by category`() {
        val s = store(testNote, workNote, ideaNote)
        s.dispatch(NoteAction.SetCategory(NoteCategory.WORK))
        assertEquals(1, s.current.filteredNotes.size)
        assertEquals("Work", s.current.filteredNotes[0].title)
    }

    @Test
    fun `clear category shows all`() {
        val s = store(testNote, workNote)
        s.dispatch(NoteAction.SetCategory(NoteCategory.WORK))
        s.dispatch(NoteAction.SetCategory(null))
        assertEquals(2, s.current.filteredNotes.size)
    }

    @Test
    fun `search filters by title`() {
        val s = store(testNote, workNote, ideaNote)
        s.dispatch(NoteAction.SetSearch("work"))
        assertEquals(1, s.current.filteredNotes.size)
    }

    @Test
    fun `search filters by body`() {
        val s = store(testNote, workNote, ideaNote)
        s.dispatch(NoteAction.SetSearch("concept"))
        assertEquals(1, s.current.filteredNotes.size)
        assertEquals("Idea", s.current.filteredNotes[0].title)
    }

    @Test
    fun `search is case insensitive`() {
        val s = store(testNote)
        s.dispatch(NoteAction.SetSearch("TEST"))
        assertEquals(1, s.current.filteredNotes.size)
    }

    @Test
    fun `category and search combine`() {
        val s = store(testNote, workNote, ideaNote)
        s.dispatch(NoteAction.SetCategory(NoteCategory.IDEAS))
        s.dispatch(NoteAction.SetSearch("concept"))
        assertEquals(1, s.current.filteredNotes.size)
    }

    @Test
    fun `syncing flag`() {
        val s = store()
        s.dispatch(NoteAction.SetSyncing(true))
        assertTrue(s.current.syncing)
    }
}
