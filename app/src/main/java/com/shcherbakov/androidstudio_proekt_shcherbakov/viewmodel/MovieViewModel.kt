package com.shcherbakov.androidstudio_proekt_shcherbakov.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shcherbakov.androidstudio_proekt_shcherbakov.data.Movie
import com.shcherbakov.androidstudio_proekt_shcherbakov.data.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    data class UiState(
        val movie: Movie? = null,
        val reviews: List<String> = emptyList(),
        val popularityIndex: Int = 0,
        val isLoading: Boolean = false,
        val error: String? = null,
        val progressText: String = ""
    )

    private val repository = MovieRepository()
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadMovieData(movieId: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true, progressText = "Загрузка данных...")
            try {
                // coroutineScope защищает родителя от краша при ошибке в дочерних корутинах
                coroutineScope {
                    val detailsDeferred = async { repository.fetchDetails(movieId) }
                    val reviewsDeferred = async { repository.fetchReviews(movieId) }

                    _uiState.value = _uiState.value.copy(progressText = "Анализ рейтинга...")

                    // Ждём завершения параллельных запросов
                    val movie = detailsDeferred.await()
                    val reviews = reviewsDeferred.await()

                    // Тяжёлые вычисления (автоматически перейдут на Dispatchers.Default внутри репозитория)
                    val popularity = repository.calculatePopularity(movie.rating)

                    _uiState.value = UiState(
                        movie = movie,
                        reviews = reviews,
                        popularityIndex = popularity,
                        isLoading = false,
                        progressText = "Готово!",
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка: ${e.message}",
                    progressText = ""
                )
            }
        }
    }

    fun toggleError() = repository.toggleErrorSimulation()
}