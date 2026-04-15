package com.shcherbakov.androidstudio_proekt_shcherbakov.data


data class Movie(
    val id: String,
    val title: String,
    val year: Int,
    val rating: Float,
    val director: String,
    val genre: String,
    val description: String
)

val sampleMovies = listOf(
    Movie(
        id = "1",
        title = "Начало",
        year = 2010,
        rating = 8.8f,
        director = "Кристофер Нолан",
        genre = "Фантастика",
        description = "Вор, крадущий корпоративные секреты с помощью технологии обмена снами, получает обратное задание: внедрить идею в сознание генерального директора."
    ),
    Movie(
        id = "2",
        title = "Интерстеллар",
        year = 2014,
        rating = 8.6f,
        director = "Кристофер Нолан",
        genre = "Фантастика",
        description = "Группа исследователей отправляется в космическое путешествие через червоточину в поисках нового дома для человечества."
    ),
    Movie(
        id = "3",
        title = "Тёмный рыцарь",
        year = 2008,
        rating = 9.0f,
        director = "Кристофер Нолан",
        genre = "Боевик",
        description = "Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он стремится уничтожить организованную преступность в Готэме."
    )
)