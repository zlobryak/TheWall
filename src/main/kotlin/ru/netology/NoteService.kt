package ru.netology

//    Методы для работы с заметками.
object NoteService {
    private var notes = mutableListOf<Notes>() //Все заметки
    private var deletedNotes = mutableListOf<Notes>() // Удаленные заметки
    private var nextNoteId = 1 //Уникальный Id для заметок

    private var comments = mutableListOf<Comment>() //Все комментарии
    private var deletedComments = mutableListOf<Comment>() // Все удаленные комментарии
    private var nextCommentId = 1 //Уникальный ID для комментариев

    fun clear() {
        notes.clear()
        deletedNotes.clear()
        nextNoteId = 1
        comments.clear()
        deletedComments.clear()
        nextCommentId = 1

    }

    // Создает новую заметку у текущего пользователя.
    fun addNote(noteTitle: String, noteText: String, fromId: Int? = null): Boolean {
        return notes.add(Notes(nextNoteId++, noteTitle, noteText, fromId = fromId))
    }

    // Добавляет новый комментарий к заметке.
    fun createComment(noteToCommentId: Int, text: String): Boolean {
        val notesIterator = notes.listIterator()
        while (notesIterator.hasNext()) {
            val note = notesIterator.next()
            if (note.id == noteToCommentId) {
                return comments.add(Comment(nextCommentId++, text = text, noteToCommentId = noteToCommentId))
            }
        }
        throw NoteNotFoundException("Post with $noteToCommentId is not found")
    }

    // Удаляет заметку текущего пользователя.
    fun deleteNote(noteToDeleteId: Int): Boolean {
        val notesIterator = notes.listIterator()
        while (notesIterator.hasNext()) {
            val note = notesIterator.next()
            if (note.id == noteToDeleteId) {
                notesIterator.remove()
                deletedNotes.add(note) //Храним удаленные заметки, для возможности их восстановить
                val commentsIterator = comments.listIterator()
                while (commentsIterator.hasNext()) {
                    val comment = commentsIterator.next()
                    if (comment.noteToCommentId == note.id) {
                        commentsIterator.remove()
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
        val commentIterator = comments.listIterator()
        while (commentIterator.hasNext()) {
            val comment: Comment = commentIterator.next()
            if (comment.id == commentToDeleteId) {
                commentIterator.remove()
                deletedComments.add(comment) //Храним удаленные заметки, для возможности их восстановить
                return true
            }
        }
        throw CommentNotFoundException("Post with $commentToDeleteId is not found")
    }

    // Редактирует заметку текущего пользователя.
    fun noteToEdit(noteToEditId: Int, text: String): Boolean {
        val notesIterator = notes.listIterator()
        while (notesIterator.hasNext()) {
            val note = notesIterator.next()
            if (note.id == noteToEditId) {
                note.text = text
                return true

            }
        }
        throw NoteNotFoundException("Post with $noteToEditId is not found")

    }

    // Редактирует указанный комментарий у заметки.
    fun editComment(commentToEditId: Int, text: String): Boolean {
        val commentIterator = comments.listIterator()
        while (commentIterator.hasNext()) {
            val comment: Comment = commentIterator.next()
            if (comment.id == commentToEditId) {
                comment.text = text
                return true
            }
        }
        throw CommentNotFoundException("Post with $commentToEditId is not found")
    }

    // Возвращает список заметок, созданных пользователем.
    fun get(fromId: Int): ArrayList<Notes> {
        val listOfNotesToReturn = ArrayList<Notes>()
        val notesIterator = notes.listIterator()
        while (notesIterator.hasNext()) {
            val note = notesIterator.next()
            if (note.fromId== fromId) {
                listOfNotesToReturn.add(note)

            }
        }
        return listOfNotesToReturn
    }

    // Возвращает заметку по её id.
    fun getByIdv(noteToReturnId: Int): Notes {
        val notesIterator = notes.listIterator()
        while (notesIterator.hasNext()) {
            val note = notesIterator.next()
            if (note.id == noteToReturnId) {
                return note
            }
        }
        throw NoteNotFoundException("Post with $noteToReturnId is not found")
    }

    // Возвращает список комментариев к заметке.
    fun getComments(noteId: Int): ArrayList<Comment> {
        val listOfCommentsToReturn = ArrayList<Comment>()
        val commentIterator = comments.listIterator()
        while (commentIterator.hasNext()) {
            val comment = commentIterator.next()
            if (noteId == comment.noteToCommentId) {
                listOfCommentsToReturn.add(comment)
            }
        }
        return listOfCommentsToReturn
    }

    //Возвращает коммент по ID
    fun getCommentById(commentId: Int): Comment {
        val commentIterator = comments.listIterator()
        while (commentIterator.hasNext()) {
            val comment = commentIterator.next()
            if (commentId == comment.id) {
                return comment
            }
        }
        throw CommentNotFoundException("Post with $commentId is not found")

    }

    // Восстанавливает удалённый комментарий.
    fun restoreComment(commentToRestoreId: Int): Boolean {
        val commentIterator = deletedComments.listIterator()
        while (commentIterator.hasNext()) {
            val comment = commentIterator.next()
            if (comment.id == commentToRestoreId) {
                commentIterator.remove() //Удаляем комментарий из списка удаленных
                comments.add(comment) //Возвращаем удаленный комментарий
                return true
            }
        }
        throw CommentNotFoundException("Post with $commentToRestoreId is not found")

    }

    class CommentNotFoundException(error: String) : RuntimeException()
    class NoteNotFoundException(error: String) : RuntimeException()
}
