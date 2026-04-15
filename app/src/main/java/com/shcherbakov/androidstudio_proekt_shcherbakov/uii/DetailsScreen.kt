package com.shcherbakov.androidstudio_proekt_shcherbakov.uii


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning // ✅ Иконка исправлена здесь
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shcherbakov.androidstudio_proekt_shcherbakov.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: String,
    onNavigateBack: () -> Unit,
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    // Подписываемся на состояние
    val state by viewModel.uiState.collectAsState()

    // Запускаем загрузку при открытии экрана
    LaunchedEffect(movieId) {
        viewModel.loadMovieData(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали фильма") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    // ✅ Кнопка для тестов ошибки
                    IconButton(onClick = { viewModel.toggleError() }) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Simulate Error",
                            tint = if (state.error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // 1. Состояние: ЗАГРУЗКА
            if (state.isLoading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(state.progressText, style = MaterialTheme.typography.bodyLarge)
                }
            }
            // 2. Состояние: ОШИБКА
            else if (state.error != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadMovieData(movieId) }) {
                        Text("Попробовать снова")
                    }
                }
            }
            // 3. Состояние: УСПЕХ (данные загружены)
            else if (state.movie != null) {
                val movie = state.movie!!

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Карточка с основной информацией
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Режиссёр: ${movie.director}",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    // Карточка с параметрами
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DetailRow(label = "Год", value = movie.year.toString())
                            HorizontalDivider()
                            DetailRow(label = "Жанр", value = movie.genre)
                            HorizontalDivider()
                            DetailRow(label = "Рейтинг", value = "★ ${movie.rating}")
                            HorizontalDivider()
                            DetailRow(label = "Индекс популярности", "${state.popularityIndex} (вычислено)")
                        }
                    }

                    // Карточка с описанием
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Описание",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = movie.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Отзывы
                    if (state.reviews.isNotEmpty()) {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Отзывы",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                state.reviews.forEach { Text("• $it") }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Вспомогательный компонент для строк "Ключ: Значение"
@Composable
fun DetailRow(label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}