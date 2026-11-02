package com.fittrack.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fittrack.app.data.entity.DayTask
import kotlinx.coroutines.flow.Flow

@Dao
interface DayTaskDao {

    @Query("SELECT * FROM day_tasks WHERE epochDay = :epochDay ORDER BY sortOrder ASC")
    fun observeDay(epochDay: Long): Flow<List<DayTask>>

    @Query("SELECT * FROM day_tasks WHERE epochDay BETWEEN :start AND :end ORDER BY epochDay ASC, sortOrder ASC")
    fun observeRange(start: Long, end: Long): Flow<List<DayTask>>

    @Query("SELECT COUNT(*) FROM day_tasks WHERE epochDay = :epochDay")
    suspend fun countForDay(epochDay: Long): Int

    @Query("DELETE FROM day_tasks WHERE epochDay >= :from")
    suspend fun deleteFrom(from: Long)

    @Insert
    suspend fun insertAll(tasks: List<DayTask>)

    @Query("UPDATE day_tasks SET done = :done WHERE id = :id")
    suspend fun setDone(id: Long, done: Boolean)
}
