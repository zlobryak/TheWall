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
    private var Attachment = emptyArray<Attachments>() //Массив для хранения вложений
    
    private var nextId: Int = 0

    fun add(post: Post): Post {
        post.id = nextId
        nextId = nextId + 1
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

    fun clear() {
        posts = emptyArray()
        nextId = 1

    }
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
    val comments: Comments, //Информация о комментариях к записи (поля описаны в дата классе)
    var likes: Likes, //Информация о лайках к записи (поля описаны в дата классе)
    var views: Int? = null, //Информация о просмотрах записи
    val attachment: Attachments? = null //Поле для объекта вложений

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