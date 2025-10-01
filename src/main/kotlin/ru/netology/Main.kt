package ru.netology

import java.time.LocalDateTime

fun main() {
    val comments = Comments(1)
    val likes = Likes(1)
    val post = Post(text = "Hello, Wall!", comments = comments, likes = likes)
    val (id, ownerId, createdBy, date, text) = post
    println("$id $ownerId $createdBy $date $text")

}
object WallService {
    private var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        posts += post
        return posts.last()
    }

}

data class Post(
    val id: Int = 1,
    val ownerId: Int = 1,
    val createdBy: Int = 1,
    val date: LocalDateTime = LocalDateTime.now(),
    val text: String,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = false,
    val comments: Comments,
    val likes: Likes,
    val views: Int? = null,
)


data class Likes(
    val count: Int = 0, // число пользователей, которым понравилась запись
    val userLikes: Boolean = false, //наличие отметки «Мне нравится» от текущего пользователя
    val canLike: Boolean = true, //информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val canPublish: Boolean = true, //информация о том, может ли текущий пользователь сделать репост записи
)

data class Comments(
    val count: Int = 0, //количество комментариев
    val canPost: Boolean = true, // информация о том, может ли текущий пользователь комментировать запись
    val groupsCanPost: Boolean = true,  //информация о том, могут ли сообщества комментировать запись;
    val canClose: Boolean = true, // может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean = true, //может ли текущий пользователь открыть комментарии к записи
)