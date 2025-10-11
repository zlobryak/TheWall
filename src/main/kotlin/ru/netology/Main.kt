package ru.netology

fun main() {

    /*
        val comments = Comments(1)
        val likes = Likes(1)
        val post0 = Post(text = "Hello, Wall!", comments = comments, likes = likes)
        val post1 = Post(text = "post1", comments = comments, likes = likes)
        val post2 = Post(text = "post2", comments = comments, likes = likes)
        val wall = WallService
        var postWithId = wall.add(post0)
        var postWithId1 = wall.add(post1)
        var postWithId2 = wall.add(post2)
        postWithId.text = "Hello, Wall! Edited"
        wall.update(postWithId)
     */
}

object WallService {
    private var posts = emptyArray<Post>() //Стена для постов
    private var comments = emptyArray<Comment>() //Массив для хранения комментариев
    private var reports = emptyArray<Report>() //Массив для хранения жалоб на комментарии
    private var nextPostId: Int = 0 //Счетчик для присвоения уникальных id постам
    private var nextCommentId: Int = 0 //Счетчик для присвоения уникальных id комментариям
    private var nextReportId: Int = 0 //Счетчик для присвоения уникальных id жалобам

    fun createComment(postIdToComment: Int, comment: Comment): Comment {
        for (post in posts) {
            comment.id = nextCommentId
            nextCommentId = postIdToComment + 1
            if (post.id == postIdToComment) {
                comments += comment.copy()
                return comments.last()
            }
        }
        throw PostNotFoundException("Post with $postIdToComment is not found")
    }

    fun addPost(post: Post): Post {
        post.id = nextPostId
        nextPostId += 1
        posts += post.copy()
        return posts.last()
    }

    fun update(post: Post): Boolean {
        val idToEdit = post.id

        for ((index, post) in posts.withIndex()) {
            if (post.id == idToEdit) {
                posts[index] = post.copy()
                return true
            }
        }
        return false
    }

    fun addAttachment(attachment: Attachments?): String {
        when (attachment?.type) {
            "video" -> return "video"
            "audio" -> return "audio"
            "student" -> return "student"
            "place" -> return "place"
            "file" -> return "file"
            "sticker" -> return "sticker"
        }
        return "no such attachment"
    }

    fun clear() {
        posts = emptyArray()
        nextPostId = 1

    }

    // Будем получать ID комментария и причину как параметры.
    // Тогда они могут быть неправильными
    fun addReport(ownerId: Int, commentId: Int, reason: String): Report {
        val reason = reportReasonFromString(reason)
        val report = Report(ownerId = ownerId, commentId = commentId, reason = reason)
        for (post in posts) {
            report.id = nextReportId
            nextCommentId = nextReportId + 1
            if (post.id == report.commentId) {
                reports += report.copy()
                return reports.last()
            }
        }
        throw PostNotFoundException("Post with $report.commentId is not found")
    }

    // Отдельная функция, которая будет проверять соответствие параметра reason из списка доступных причин
    fun reportReasonFromString(description: String): ReportReason {
        for (reason in ReportReason.entries) {
            if (reason.name.equals(description, ignoreCase = true)) {
                return reason
            }
        }
        throw NoSuchReasonException(
            "Недопустимая причина жалобы: '$description'."
        )
    }

    class NoSuchReasonException(message: String) : RuntimeException(message)
    class PostNotFoundException(message: String) : RuntimeException(message)
}


