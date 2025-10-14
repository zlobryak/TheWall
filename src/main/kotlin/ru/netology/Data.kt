package ru.netology

import java.time.LocalDateTime

//Класс описывающий пост
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


//Класс описывающий лайки
data class Likes(
    val count: Int = 0, // число пользователей, которым понравилась запись
    val userLikes: Boolean = false, //наличие отметки «Мне нравится» от текущего пользователя
    val canLike: Boolean = true, //информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val canPublish: Boolean = true, //информация о том, может ли текущий пользователь сделать репост записи
)

// Объект, описывающий комментарии на стене, содержит следующие поля:
data class Comment(
    var id: Int? = null, // id комментария
    var text: String, // Текст комментария
    val date: LocalDateTime = LocalDateTime.now(), //Дата создания комментария в формате
    val noteToCommentId: Int? = 0, //Id заметки, к которой оставлен комментарий
    val replyToUser: Int? = null, //Идентификатор пользователя или сообщества, в ответ которому оставлен текущий комментарий (если применимо).
    var fromId: Int? = null, // Идентификатор автора комментария.
    val canPost: Boolean = true, // информация о том, может ли текущий пользователь комментировать запись
    val groupsCanPost: Boolean = true,  //информация о том, могут ли сообщества комментировать запись;
    val canClose: Boolean = true, // может ли текущий пользователь закрыть комментарии к записи
    val canOpen: Boolean = true, //может ли текущий пользователь открыть комментарии к записи
    val attachments: List<Attachments> = emptyList(), //Массив для вложений
)

// Жалобы на комментарии к записям
data class Report(
    var id: Int? = null, //Идентификатор жалобы. Уникальный обязательный параметр, присваивается функцией
    val ownerId: Int, //Идентификатор пользователя или сообщества, которому принадлежит комментарий. Обязательный параметр
    val commentId: Int, //Идентификатор комментария. Обязательный параметр
    var reason: ReportReason // Отдельный класс, который содержит все доступные варианты для причины жалобы
)

//Варианты причин для жалобы
enum class ReportReason(description: String) {
    SPAM("Спам"),
    CHILD_PORNOGRAPHY("Детская порнография"),
    EXTREMISM("Экстремизм"),
    VIOLENCE("Призывы к насилию"),
    DRUG_PROPAGANDA("Пропаганда наркотиков"),
    ADULT_CONTENT("Контент для взрослых"),
    INSULT("Оскорбление"),
    SUICIDE_CALLS("Призывы к суициду");
}

// Заметки
data class Notes(
    val id: Int? = null, // Уникальный Id
    val title: String, // Заголовок заметки. Обязательный параметр
    var text: String, // Текст заметки. Обязательный параметр
    var fromId: Int? = null, // Идентификатор автора заметки
    val privacy: PrivacyLevel = PrivacyLevel.ALL, // Уровень доступа к заметке. Возможные значения:
    val commentPrivacy: PrivacyLevel = PrivacyLevel.ALL // Уровень доступа к комментированию заметки. Возможные значения:
)

// Уровни доступа
enum class PrivacyLevel {
    ALL, // все пользователи,
    OnlyFriends, // только друзья,
    OnlyFriendsAndTheyFriends, // друзья и друзья друзей,
    OwnerOnly; // только пользователь.
}