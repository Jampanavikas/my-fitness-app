package com.fittrack.app.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fittrack.app.data.entity.WeightEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Query("SELECT * FROM weight_entries ORDER BY epochDay ASC")
    fun observeAll(): Flow<List<WeightEntry>>

    @Upsert
    suspend fun upsert(entry: WeightEntry)
}
