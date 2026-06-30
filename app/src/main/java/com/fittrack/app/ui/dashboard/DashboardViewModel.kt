package com.fittrack.app.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fittrack.app.FitTrackApp
import com.fittrack.app.data.entity.DayTask
import com.fittrack.app.data.entity.TaskCategory
import com.fittrack.app.notify.ReminderScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

data class DayStat(
    val label: String,
    val dayOfMonth: Int,
    val done: Int,
    val total: Int,
    val fraction: Float,
    val isToday: Boolean,
    val isFuture: Boolean,
    val workoutDone: Boolean,
)

data class DashboardUiState(
    val rangeLabel: String = "",
    val days: List<DayStat> = emptyList(),
    val workoutsDone: Int = 0,
    val workoutsTotal: Int = 6,
    val tasksDone: Int = 0,
    val tasksTotal: Int = 0,
    val avgCompletion: Int = 0,
    val streak: Int = 0,
)

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = (app as FitTrackApp).repository
    private val today: LocalDate = LocalDate.now()
    private val monday: LocalDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    private val sunday: LocalDate = monday.plusDays(6)

    private val workoutCats = setOf(
        TaskCategory.WARMUP.name, TaskCategory.WORKOUT.name, TaskCategory.SPORT.name
    )

    init {
        viewModelScope.launch { repo.ensureWeekGenerated(monday) }
    }

    val remindersEnabled: StateFlow<Boolean> =
        repo.remindersEnabled.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

    fun setReminders(enabled: Boolean) {
        viewModelScope.launch {
            repo.setRemindersEnabled(enabled)
            val ctx = getApplication<Application>()
            if (enabled) ReminderScheduler.scheduleAll(ctx) else ReminderScheduler.cancelAll(ctx)
        }
    }

    val uiState: StateFlow<DashboardUiState> =
        repo.observeRange(monday, sunday).map { all ->
            val byDay = all.groupBy { it.epochDay }
            val dayStats = (0..6).map { i ->
                val date = monday.plusDays(i.toLong())
                val tasks = byDay[date.toEpochDay()] ?: emptyList()
                val total = tasks.size
                val done = tasks.count { it.done }
                val workoutTasks = tasks.filter { it.category in workoutCats }
                DayStat(
                    label = oneLetter(date.dayOfWeek),
                    dayOfMonth = date.dayOfMonth,
                    done = done,
                    total = total,
                    fraction = if (total == 0) 0f else done.toFloat() / total,
                    isToday = date == today,
                    isFuture = date.isAfter(today),
                    workoutDone = workoutTasks.isNotEmpty() && workoutTasks.all { it.done },
                )
            }

            val pastOrToday = dayStats.filter { !it.isFuture }
            val avg = if (pastOrToday.isEmpty()) 0
            else (pastOrToday.map { it.fraction }.average() * 100).toInt()

            DashboardUiState(
                rangeLabel = "${monday.format(FMT)} – ${sunday.format(FMT)}",
                days = dayStats,
                workoutsDone = dayStats.count { it.workoutDone },
                tasksDone = all.count { it.done },
                tasksTotal = all.size,
                avgCompletion = avg,
                streak = computeStreak(dayStats),
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())

    /** Consecutive fully-complete days ending today. */
    private fun computeStreak(days: List<DayStat>): Int {
        var streak = 0
        val upTo = days.filter { !it.isFuture }
        for (d in upTo.reversed()) {
            if (d.total > 0 && d.done == d.total) streak++ else break
        }
        return streak
    }

    private fun oneLetter(d: DayOfWeek): String = when (d) {
        DayOfWeek.MONDAY -> "M"
        DayOfWeek.TUESDAY -> "T"
        DayOfWeek.WEDNESDAY -> "W"
        DayOfWeek.THURSDAY -> "T"
        DayOfWeek.FRIDAY -> "F"
        DayOfWeek.SATURDAY -> "S"
        DayOfWeek.SUNDAY -> "S"
    }

    companion object {
        private val FMT: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")
    }
}
