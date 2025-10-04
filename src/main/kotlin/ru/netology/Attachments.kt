package ru.netology

import java.time.LocalDateTime

interface Attachments {
    val type: String
}

//Объект, описывающий видеозапись
class VideoAttachment(
    override val type: String = "video",
    val id: Int? = null,    // Идентификатор видеозаписи.
    val ownerId: Int?, // Идентификатор владельца видеозаписи.
    val title: String? = null, // Название видеозаписи.
    val description: String? = null, //    string Текст описания видеозаписи.
    val duration: Int? = null, //    integer Длительность ролика в секундах.
    val image: Image, //Изображение обложки.
) : Attachments

//Объект, описывающий аудиозапись
class AudioAttachment(
    override val type: String = "Audio",
    val id: Int? = null,    // Идентификатор аудиозаписи.
    val ownerId: Int, // Идентификатор владельца аудиозаписи.
    val artist: String? = null, // Исполнитель.
    val title: String? = null, // Название композиции.
    val duration: Int? = null, // Длительность аудиозаписи в секундах.
    val url: String? = null, // Ссылка на mp3.
    val image: Image, //Изображение обложки.
) : Attachments

//All in all, it's just another brick in the wall
class AnotherBrickInTheWallAttachment(
    override val type: String = "Student",
    val id: Int? = null, // Идентификатор студента
    val name: String, //Имя студента
    val surname: String, //Фамилия студента
    val age: Int? = null, //Возраст студента
    val image: Image, //Аватарка студента.
) : Attachments

//Объект, описывающий геометку
class PlaceAttachment(
    override val type: String = "Place",
    val coordinates: Coordinates
) : Attachments

//Объект, описывающий файл,
class FileAttachment(
    override val type: String = "File",
    val id: Int? = null, // Идентификатор файла.
    val ownerId: Int, // Идентификатор пользователя, загрузившего файл.
    val title: Int, // Название файла.
    val size: Int? = null, // Размер файла в байтах.
    val ext: String, // Расширение файла.
    val url: String? = null, // Адрес файла, по которому его можно загрузить.
    val date: LocalDateTime = LocalDateTime.now(), // Дата добавления.
    val fileType: Int// Тип файла.
    /*
    Возможные значения для типа файла:
1 — текстовые документы;
2 — архивы;
3 — gif;
4 — изображения;
5 — аудио;
6 — видео;
7 — электронные книги;
8 — неизвестно.
 */
) : Attachments

//Объект, описывающий стикер,
class StickerAttachment(
    override val type: String = "File",
    val productId: Int, // Идентификатор набора.
    val stickerId: Int, //Идентификатор стикера.
    val animationUrl: String? = null, // URL анимации стикера.
    val isAllowed: Boolean, //Информация о том, доступен ли стикер.
) : Attachments

//Дата класс для объектов координат в геометках
data class Coordinates(
    val latitude: Int,//географическая широта;
    val longitude: Int // географическая долгота.
)

//Дата класс для объектов изображений
data class Image(
    val url: String, // ссылка на изображение.
    val height: Int, // высота изображения.
    val width: Int // ширина изображения.
)