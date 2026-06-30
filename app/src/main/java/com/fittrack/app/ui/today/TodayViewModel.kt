package com.fittrack.app.ui.today

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fittrack.app.FitTrackApp
import com.fittrack.app.data.RoutineTemplate
import com.fittrack.app.data.entity.DayTask
import com.fittrack.app.data.entity.TaskCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class TodayUiState(
    val greeting: String = "Hello",
    val dateLabel: String = "",
    val tasks: List<DayTask> = emptyList(),
    val consumedKcal: Int = 0,
    val targetKcal: Int = RoutineTemplate.CALORIE_TARGET,
    val consumedProtein: Int = 0,
    val targetProtein: Int = RoutineTemplate.PROTEIN_TARGET,
    val doneCount: Int = 0,
    val totalCount: Int = 0,
    val water: Int = 0,
    val waterTarget: Int = RoutineTemplate.WATER_TARGET_GLASSES,
)

class TodayViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = (app as FitTrackApp).repository
    private val today: LocalDate = LocalDate.now()

    init {
        viewModelScope.launch { repo.ensureDayGenerated(today) }
    }

    val uiState: StateFlow<TodayUiState> =
        combine(repo.observeDay(today), repo.waterGlasses(today)) { tasks, water ->
            val consumedMeals = tasks.filter { it.done && it.category == TaskCategory.MEAL.name }
            TodayUiState(
                greeting = greeting(),
                dateLabel = today.format(DateTimeFormatter.ofPattern("EEEE • d MMM")),
                tasks = tasks,
                consumedKcal = consumedMeals.sumOf { it.kcal },
                consumedProtein = consumedMeals.sumOf { it.protein },
                doneCount = tasks.count { it.done },
                totalCount = tasks.size,
                water = water,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TodayUiState())

    fun toggle(task: DayTask) {
        viewModelScope.launch { repo.setDone(task.id, !task.done) }
    }

    fun addWater(delta: Int) {
        viewModelScope.launch {
            val current = uiState.value.water
            repo.setWater(today, current + delta)
        }
    }

    private fun greeting(): String = when (LocalTime.now().hour) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..21 -> "Good evening"
        else -> "Late night"
    }
}
