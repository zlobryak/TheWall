package ru.netology

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NoteServiceTest {
    var notes = NoteService

    @Before
    fun setUp() {
        notes.clear()
    }

    @Test
    fun addNoteShouldReturnTrue() {
        assertTrue(notes.addNote("Test title", "Test text"))
    }

    @Test
    fun createCommentShouldReturnTrue() {
        notes.addNote("Test title", "Test text")
        assertTrue(notes.createComment(1, "Test text"))
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun createCommentShouldThrowError() {
        notes.addNote("Test title", "Test text")
        notes.createComment(2, "Test text")
    }

    @Test
    fun deleteNoteShouldReturnTrue() {
        notes.addNote("Test title1", "Test text")
        notes.addNote("Test title2", "Test text")
        assertTrue(notes.deleteNote(1))
    }


    @Test
    fun deleteNoteShouldDeleteNote() {
        notes.addNote("Test title1", "Test text", fromId = 1)
        notes.addNote("Test title2", "Test text", fromId = 1)
        notes.deleteNote(1)
        assertEquals(1, notes.get(1).size)
    }

    @Test
    fun deleteNoteShouldDeleteComment() {
        notes.addNote("Test title1", "Test text", fromId = 1)
        notes.addNote("Test title2", "Test text", fromId = 1)
        notes.createComment(1, "Test text1")
        notes.createComment(1, "Test text2")
        notes.deleteNote(1)
        assertEquals(0, notes.getComments(1).size)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun deleteNoteShouldThrowError() {
        notes.addNote("Test title1", "Test text")
        notes.addNote("Test title2", "Test text")
        notes.deleteNote(10)
    }

    @Test
    fun deleteCommentShouldReturnTrue() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        assertTrue(notes.deleteComment(1))
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun deleteCommentShouldReturnError() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.deleteComment(2)
    }

    @Test
    fun noteToEditShouldReturnTrue() {
        notes.addNote("Test title1", "Test text")
        assertTrue(notes.noteToEdit(1, "Test edit"))
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun noteToEditShouldReturnError() {
        notes.addNote("Test title1", "Test text")
        assertTrue(notes.noteToEdit(2, "Test edit"))
    }

    @Test
    fun noteToEditShouldEditText() {
        notes.addNote("Test title1", "Test text")
        notes.noteToEdit(1, "Test edit")
        assertEquals("Test edit", notes.getByIdv(1).text)
    }


    @Test
    fun editCommentShouldReturnTrue() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        assertTrue(notes.editComment(1, "Test edit"))
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun editCommentShouldThrowError() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.editComment(2, "Test edit")
    }

    @Test
    fun editCommentShouldShouldEdit() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.editComment(1, "Test edit")
        assertEquals("Test edit", notes.getCommentById(1).text)
    }

    @Test
    fun getShouldReturnList() {
        notes.addNote("Test title1", "Test text", fromId = 1)
        notes.addNote("Test title2", "Test text", fromId = 1)
        assertEquals(2, notes.get(1).size)
    }

    @Test
    fun getShouldReturnTitles() {
        notes.addNote("Test title1", "Test text1", fromId = 1)
        notes.addNote("Test title2", "Test text2", fromId = 1)
        val notesList = notes.get(1)
        assertEquals("Test title1", notesList[0].title)
    }

    @Test
    fun getShouldReturnText() {
        notes.addNote("Test title1", "Test text1", fromId = 1)
        notes.addNote("Test title2", "Test text2", fromId = 1)
        val notesList = notes.get(1)
        assertEquals("Test text1", notesList[0].text)
    }

    @Test(expected = NoteService.NoteNotFoundException::class)
    fun getByIdvSShouldThrowError() {
        notes.addNote("Test title1", "Test text")
        notes.getByIdv(2)
    }

    @Test
    fun getCommentsShouldReturnList() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text1")
        notes.createComment(1, "Test text2")
        assertEquals(2, notes.getComments(1).size)
    }

    @Test
    fun getCommentsShouldReturnText() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text1")
        notes.createComment(1, "Test text2")
        assertEquals("Test text1", notes.getComments(1).get(0).text)
    }

    @Test
    fun restoreCommentShouldReturnTrue() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.deleteComment(1)
        assertTrue(notes.restoreComment(1))
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun restoreCommentShouldThrowError() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.deleteComment(1)
        notes.restoreComment(2)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun deleteCommentShouldThrowError() {
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.deleteComment(2)
    }

    @Test(expected = NoteService.CommentNotFoundException::class)
    fun getCommentByIdShouldThrowError(){
        notes.addNote("Test title1", "Test text")
        notes.createComment(1, "Test text")
        notes.getCommentById(2)
    }
}