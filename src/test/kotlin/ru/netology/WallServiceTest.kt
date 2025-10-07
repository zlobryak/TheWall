package ru.netology

import kotlin.test.*

class WallServiceTest {

    val wall = WallService
    val likes = Likes(1)
    val comment = Comment(1, text = "Test text")

    @BeforeTest
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun createComment() {
        wall.add(post = Post(ownerId = 1, text = "test text", likes = likes))
        wall.add(post = Post(ownerId = 2, text = "test text", likes = likes))
        wall.add(post = Post(ownerId = 3, text = "test text", likes = likes))
        assertEquals(comment, wall.createComment(1, comment))
    }

    @Test(expected = WallService.PostNotFoundException::class)
    fun createCommentShouldThrow() {
        wall.createComment(1, comment)
    }

    @Test
    fun addChangesId() {
        val post0 = Post(text = "Hello, Wall!", likes = likes)
        assertEquals(wall.add(post0).id, 1)
    }

    @Test
    fun updateReturnsTrue() {
        val post0 = Post(text = "Hello, Wall!", likes = likes)
        val postWithId = wall.add(post0)
        postWithId.text = "Hello, Wall! Edited"
        assertTrue { wall.update(postWithId) }
    }

    @Test
    fun updateReturnsFalse() {
        val post0 = Post(text = "Hello, Wall!", likes = likes)
        wall.add(post0)
        val notAddedPost = Post(id = 10001, text = "No text", likes = likes)

        assertFalse { wall.update(notAddedPost) }
    }

    @Test
    fun addAttachmentShouldReturnStudent() {
        val student = Student(id = 1, name = "Floyd", surname = "Pinkerton")
        val attachment = AnotherBrickInTheWallAttachment("student", student)
        assertEquals("student", wall.addAttachment(attachment))
    }

}