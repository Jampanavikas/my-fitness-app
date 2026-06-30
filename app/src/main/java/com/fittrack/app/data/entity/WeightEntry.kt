package com.fittrack.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** A logged body-weight reading. Foundation for the Progress screen (phase 2). */
@Entity(tableName = "weight_entries")
data class WeightEntry(
    @PrimaryKey val epochDay: Long,
    val weightKg: Float,
)
