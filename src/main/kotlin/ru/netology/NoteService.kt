package ru.netology

//    Методы для работы с заметками.
class NoteService() {
    private var notes = mutableListOf<Notes>() //Все заметки
    private var deletedNotes = mutableListOf<Notes>() // Удаленные заметки
    private var nextNoteId = 0 //Уникальный Id для заметок

    private var comments = mutableListOf<Comment>() //Все комментарии
    private var deletedComments = mutableListOf<Comment>() // Все удаленные комментарии
    private var nextCommentId = 0 //Уникальный ID для комментариев

    // Создает новую заметку у текущего пользователя.
    fun addNote(noteTitle: String, noteText: String): Boolean {
        return notes.add(Notes(nextNoteId++, noteTitle, noteText))
    }

    // Добавляет новый комментарий к заметке.
    fun createComment(noteToCommentId: Int, text: String): Boolean {
        return comments.add(Comment(text = text, noteToCommentId = noteToCommentId))
    }

    // Удаляет заметку текущего пользователя.
    fun deleteNote(noteToDeleteId: Int): Boolean {
        while (notes.listIterator().hasNext()) {
            val note = notes.listIterator().next()
            if (note.id == noteToDeleteId) {
                notes.remove(note)
                deletedNotes.add(note) //Храним удаленные заметки, для возможности их восстановить

                while (comments.listIterator().hasNext()) {
                    val comment = comments.listIterator().next()
                    if (comment.noteToCommentId == note.id) {
                        comments.remove(comment)
                        deletedComments.add(comment) //Храним удаленные комментарии
                    }
                }
                return true
            }
        }
        throw NoteNotFoundException("Post with $noteToDeleteId is not found")
    }

    // Удаляет комментарий к заметке.
    fun deleteComment(commentToDeleteId: Int): Boolean {
        while (comments.listIterator().hasNext()) {
            val comment: Comment = comments.listIterator().next()
            if (comment.id == commentToDeleteId) {
                comments.remove(comment)
                deletedComments.add(comment) //Храним удаленные заметки, для возможности их восстановить
                return true
            }
        }
        throw CommentNotFoundException("Post with $commentToDeleteId is not found")
    }

    // Редактирует заметку текущего пользователя.
    fun noteToEdit(noteToEditId: Int, text: String): Boolean {
        while (notes.listIterator().hasNext()) {
            val note = notes.listIterator().next()
            if (note.id == noteToEditId) {
                note.text = text
            }
            return true
        }
        throw NoteNotFoundException("Post with $noteToEditId is not found")

    }

    // Редактирует указанный комментарий у заметки.
    fun editComment(commentToEditId: Int, text: String): Boolean {
        while (comments.listIterator().hasNext()) {
            val comment: Comment = comments.listIterator().next()
            if (comment.id == commentToEditId) {
                comment.text = text
            }
        }
        throw CommentNotFoundException("Post with $commentToEditId is not found")
    }

    // Возвращает список заметок, созданных пользователем.
    fun get(idList: List<Int>): ArrayList<Notes> {
        var listOfNotesToReturn = ArrayList<Notes>()
        while (idList.listIterator().hasNext()) {
            val id = idList.listIterator().next()
            while (notes.listIterator().hasNext()) {
                val note = notes.listIterator().next()
                if (id == note.id) {
                    listOfNotesToReturn.add(note)
                }
            }
        }
        return listOfNotesToReturn
    }

    // Возвращает заметку по её id.
    fun getByIdv(noteToReturnId: Int): Notes {
        while (notes.listIterator().hasNext()) {
            val note = notes.listIterator().next()
            if (note.id == noteToReturnId) {
                return note
            }
        }
        throw NoteNotFoundException("Post with $noteToReturnId is not found")
    }

    // Возвращает список комментариев к заметке.
    fun getComments(noteId: Int): ArrayList<Comment> {
        var listOfCommentsToReturn = ArrayList<Comment>()
        while (comments.listIterator().hasNext()) {
            val comment = comments.listIterator().next()
            if (noteId == comment.id) {
                listOfCommentsToReturn.add(comment)
            }
        }
        return listOfCommentsToReturn
    }

    // Восстанавливает удалённый комментарий.
    fun restoreComment(commentToRestoreId: Int): Boolean {
        while (deletedComments.listIterator().hasNext()) {
            val comment = deletedComments.listIterator().next()
            if (comment.id == commentToRestoreId) {
                deletedComments.remove(comment)
                comments.add(comment)
                return true
            }
        }
        throw CommentNotFoundException("Post with $commentToRestoreId is not found")

    }
}

class CommentNotFoundException(error: String) : RuntimeException()
class NoteNotFoundException(error: String) : RuntimeException()
