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

//    @Test
//    fun addReportShouldThrow() {
//        //TODO Нексколько вариантов ошибок
//    }

    @Test
    fun addReportShouldReturnReportSPAM() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.SPAM)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportCHILD_PORNOGRAPHY() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.CHILD_PORNOGRAPHY)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportEXTREMISM() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.EXTREMISM)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportVIOLENCE() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.VIOLENCE)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportDRUG_PROPAGANDA() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.DRUG_PROPAGANDA)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportADULT_CONTENT() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.ADULT_CONTENT)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportINSULT() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.INSULT)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
    }

    @Test
    fun addReportShouldReturnReportSUICIDE_CALLS() {
        val report = Report(ownerId = 1, commentId = 1, reason = ReportReason.SUICIDE_CALLS)
        wall.addPost(post = Post(ownerId = 1, text = "test text", likes = likes))
        assertEquals(report, wall.addReport(report))
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