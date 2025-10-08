package ru.netology

import java.time.LocalDateTime

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

    class PostNotFoundException(message: String) : RuntimeException(message)

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

        fun addReport(report: Report): Report {
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
}

// Жалобы на комменатрии к записям
data class Report(
    var id: Int? = null, //Идлентификатор жалобы. Уникальный обязательный паарметр, присваивается функцией
    val ownerId: Int, //Идентификатор пользователя или сообщества, которому принадлежит комментарий. Обязательный параметр
    val commentId: Int, //Идентификатор комментария. Обязательный параметр
    var reason: ReportReason? = null, //TODO Набор вариантов заполнения поля надо ограничить
)

//Виды причин на жалобу
enum class ReportReason {
    SPAM,
    CHILD_PORNOGRAPHY,
    EXTREMISM,
    VIOLENCE,
    DRUG_PROPAGANDA,
    ADULT_CONTENT,
    INSULT,
    SUICIDE_CALLS;
    //TODO Выбрасывать код ошибки с текстом
}

data class Post(
    var id: Int? = null, //Идентификатор записи
    val ownerId: Int = 1, //Идентификатор владельца стены, на которой размещена запись
    val fromId: Int = 1, //Идентификатор автора записи (от чьего имени опубликована запись)
    val date: LocalDateTime = LocalDateTime.now(), //Время публикации записи в формате
    var text: String, //Текст записи
    val replyOwnerId: Int? = null, //Идентификатор владельца записи, в ответ на которую была оставлена текущая
    val replyPostId: Int? = null, //Идентификатор записи, в ответ на которую была оставлена текущая
    val friendsOnly: Boolean = false, //true если запись была создана с опцией «Только для друзей»
    var likes: Likes, //Информация о лайках к записи (поля описаны в дата классе)
    var views: Int? = null, //Информация о просмотрах записи
    val attachments: List<Attachments> = emptyList(),
)


data class Likes(
    val count: Int = 0, // число пользователей, которым понравилась запись
    val userLikes: Boolean = false, //наличие отметки «Мне нравится» от текущего пользователя
    val canLike: Boolean = true, //информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val canPublish: Boolean = true, //информация о том, может ли текущий пользователь сделать репост записи
)

// Объект, описывающий комментарии на стене, содержит следующие поля:
data class Comment(
    var id: Int? = null, // id комментария
    val text: String, // Текст комментария
    val date: LocalDateTime = LocalDateTime.now(), //Дата создания комментария в формате
    val replyToUser: Int? = null, //Идентификатор пользователя или сообщества,
    // в ответ которому оставлен текущий комментарий (если применимо).
    var fromId: Int? = null, // Идентификатор автора комментария.
    val canPost: Boolean = true, // информация о том, может ли текущий пользователь комментировать запись
    val groupsCanPost: Boolean = true,  //информация о том, могут ли сообщества комментировать запись;
    val canClose: Boolean = true, // может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean = true, //может ли текущий пользователь открыть комментарии к записи
    val attachments: List<Attachments> = emptyList(), //Массив для вложений
)