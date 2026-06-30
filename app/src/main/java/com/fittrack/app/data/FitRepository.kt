package com.fittrack.app.data

import android.content.Context
import com.fittrack.app.data.dao.DayTaskDao
import com.fittrack.app.data.dao.WeightDao
import com.fittrack.app.data.entity.DayTask
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/** Single entry point the UI layer talks to. */
class FitRepository(context: Context) {

    private val db = AppDatabase.get(context)
    private val dayTaskDao: DayTaskDao = db.dayTaskDao()
    private val weightDao: WeightDao = db.weightDao()
    val prefs = UserPrefs(context)

    fun observeDay(date: LocalDate): Flow<List<DayTask>> =
        dayTaskDao.observeDay(date.toEpochDay())

    fun observeRange(start: LocalDate, end: LocalDate): Flow<List<DayTask>> =
        dayTaskDao.observeRange(start.toEpochDay(), end.toEpochDay())

    /** Generate the day's checklist from the routine the first time it's opened. */
    suspend fun ensureDayGenerated(date: LocalDate) {
        val epochDay = date.toEpochDay()
        if (dayTaskDao.countForDay(epochDay) == 0) {
            dayTaskDao.insertAll(RoutineTemplate.buildDay(epochDay, date.dayOfWeek))
        }
    }

    /** Ensure all 7 days of the week starting [monday] exist. */
    suspend fun ensureWeekGenerated(monday: LocalDate) {
        for (i in 0..6) ensureDayGenerated(monday.plusDays(i.toLong()))
    }

    suspend fun setDone(id: Long, done: Boolean) = dayTaskDao.setDone(id, done)

    fun waterGlasses(date: LocalDate): Flow<Int> = prefs.waterGlasses(date.toEpochDay())

    suspend fun setWater(date: LocalDate, glasses: Int) =
        prefs.setWater(date.toEpochDay(), glasses)

    fun observeWeights() = weightDao.observeAll()

    val remindersEnabled: Flow<Boolean> get() = prefs.remindersEnabled
    suspend fun remindersEnabledOnce(): Boolean = prefs.remindersEnabledOnce()
    suspend fun setRemindersEnabled(enabled: Boolean) = prefs.setRemindersEnabled(enabled)
}
