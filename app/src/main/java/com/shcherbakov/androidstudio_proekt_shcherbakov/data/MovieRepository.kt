package com.shcherbakov.androidstudio_proekt_shcherbakov.data


import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MovieRepository {
    private var simulateError = false
    fun toggleErrorSimulation() { simulateError = !simulateError }

    suspend fun fetchDetails(movieId: String): Movie {
        delay(2000) // Имитация сети (Dispatchers.IO по умолчанию в ViewModel)
        if (simulateError) throw Exception("Сервер недоступен")
        return sampleMovies.find { it.id == movieId }
            ?: throw Exception("Фильм не найден")
    }

    suspend fun fetchReviews(movieId: String): List<String> {
        delay(1500)
        if (simulateError) throw Exception("Не удалось загрузить отзывы")
        return listOf("Шедевр!", "Сюжет держит до конца", "Актерская игра на высоте")
    }

    // Тяжёлое вычисление (имитация обработки данных)
    suspend fun calculatePopularity(rating: Float): Int {
        // Переключаемся на поток для вычислений, чтобы не блокировать UI
        return withContext(Dispatchers.Default) {
            var score = 0
            repeat(500_000) { score += Random.nextInt(100) }
            (score / 500_000 * rating).toInt()
        }
    }
}