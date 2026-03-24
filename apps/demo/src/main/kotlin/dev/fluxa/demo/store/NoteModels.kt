package dev.fluxa.demo.store

data class Note(
    val id: String,
    val title: String,
    val body: String,
    val category: NoteCategory = NoteCategory.GENERAL,
    val createdAt: Long = System.currentTimeMillis(),
)

enum class NoteCategory(val label: String) {
    GENERAL("General"),
    WORK("Work"),
    PERSONAL("Personal"),
    IDEAS("Ideas"),
}

data class NoteState(
    val notes: List<Note> = emptyList(),
    val selectedCategory: NoteCategory? = null,
    val searchQuery: String = "",
    val syncing: Boolean = false,
) {
    val filteredNotes: List<Note>
        get() {
            var result = notes
            selectedCategory?.let { cat ->
                result = result.filter { it.category == cat }
            }
            if (searchQuery.isNotBlank()) {
                val q = searchQuery.lowercase()
                result = result.filter {
                    it.title.lowercase().contains(q) || it.body.lowercase().contains(q)
                }
            }
            return result.sortedByDescending { it.createdAt }
        }
}

sealed class NoteAction {
    data class Add(val note: Note) : NoteAction()
    data class Update(val note: Note) : NoteAction()
    data class Delete(val noteId: String) : NoteAction()
    data class SetCategory(val category: NoteCategory?) : NoteAction()
    data class SetSearch(val query: String) : NoteAction()
    data class SetSyncing(val syncing: Boolean) : NoteAction()
    data class LoadAll(val notes: List<Note>) : NoteAction()
}
