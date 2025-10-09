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
    fun addReportShouldReturnReportSPAM() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "SPAM")
        assertEquals(ReportReason.SPAM, report.reason)
    }

    @Test
    fun addReportShouldReturnReportCHILD_PORNOGRAPHY() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "CHILD_PORNOGRAPHY")
        assertEquals(ReportReason.CHILD_PORNOGRAPHY, report.reason)
    }

    @Test
    fun addReportShouldReturnReportEXTREMISM() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "EXTREMISM")
        assertEquals(ReportReason.EXTREMISM, report.reason)
    }

    @Test
    fun addReportShouldReturnReportVIOLENCE() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "VIOLENCE")
        assertEquals(ReportReason.VIOLENCE, report.reason)
    }

    @Test
    fun addReportShouldReturnReportDRUG_PROPAGANDA() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "DRUG_PROPAGANDA")
        assertEquals(ReportReason.DRUG_PROPAGANDA, report.reason)
    }

    @Test
    fun addReportShouldReturnReportADULT_CONTENT() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "ADULT_CONTENT")
        assertEquals(ReportReason.ADULT_CONTENT, report.reason)
    }

    @Test
    fun addReportShouldReturnReportINSULT() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "INSULT")
        assertEquals(ReportReason.INSULT, report.reason)
    }

    @Test
    fun addReportShouldReturnReportSUICIDE_CALLS() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        val report = wall.addReport(ownerId = 1, commentId = 1, reason = "SUICIDE_CALLS")
        assertEquals(ReportReason.SUICIDE_CALLS, report.reason)
    }

    @Test(expected = WallService.NoSuchReasonException::class)
    fun addReportShouldThrowError() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        wall.addReport(ownerId = 1, commentId = 1, reason = "NO_SUCH_REASON")
    }

    @Test
    fun createCommentShouldReturnComment() {
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        wall.addPost(post = Post(ownerId = 2, text = "test text", likes = likes))
        wall.addPost(post = Post(ownerId = 3, text = "test text", likes = likes))
        assertEquals(comment, wall.createComment(1, comment))
    }

    @Test(expected = WallService.PostNotFoundException::class)
    fun createCommentShouldThrow() {
        wall.createComment(1, comment)
    }

    @Test
    fun addPostChangesId() {
        val post0 = Post(text = "Hello, Wall!", likes = likes)
        assertEquals(wall.addPost(post0).id, 1)
    }

    @Test
    fun updateReturnsTrue() {
        val post0 = Post(text = "Hello, Wall!", likes = likes)
        val postWithId = wall.addPost(post0)
        postWithId.text = "Hello, Wall! Edited"
        assertTrue { wall.update(postWithId) }
    }

    @Test
    fun updateReturnsFalse() {
        val post0 = Post(text = "Hello, Wall!", likes = likes)
        wall.addPost(post0)
        val notAddedPost = Post(id = 10001, text = "No text", likes = likes)

        assertFalse { wall.update(notAddedPost) }
    }

    @Test
    fun addPostAttachmentShouldReturnStudent() {
        val student = Student(id = 1, name = "Floyd", surname = "Pinkerton")
        val attachment = AnotherBrickInTheWallAttachment("student", student)
        assertEquals("student", wall.addAttachment(attachment))
    }

}