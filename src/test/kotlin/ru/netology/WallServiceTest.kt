package ru.netology

import kotlin.test.*

class WallServiceTest {
    val wall = WallService
    val likes = Likes(1)
    val comments = Comments(1)

    @BeforeTest
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addChangesId() {
        val post0 = Post(text = "Hello, Wall!", comments = comments, likes = likes)
        assertEquals(wall.add(post0).id, 1)
    }

    @Test
    fun updateReturnsTrue() {
        val post0 = Post(text = "Hello, Wall!", comments = comments, likes = likes)
        val postWithId = wall.add(post0)
        postWithId.text = "Hello, Wall! Edited"
        assertTrue { wall.update(postWithId) }
    }

    @Test
    fun updateReturnsFalse() {
        val post0 = Post(text = "Hello, Wall!", comments = comments, likes = likes)
        wall.add(post0)
        val notAddedPost = Post(id = 10001, text = "No text", comments = comments, likes = likes)

        assertFalse { wall.update(notAddedPost) }
    }

    @Test
    fun addAttachmentShouldReturnStudent() {
        val attachment = AnotherBrickInTheWallAttachment("student", id = 1, name = "Floyd", surname = "Pinkerton")
        assertEquals("student", wall.addAttachment(attachment))
    }

}