package com.fittrack.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single item on a given day's checklist: a meal, the morning walk, the
 * learning block, or the workout. Generated from the routine template on the
 * first time a day is opened, then toggled by the user.
 */
@Entity(tableName = "day_tasks")
data class DayTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val epochDay: Long,
    val category: String,   // see TaskCategory
    val title: String,
    val subtitle: String = "",
    val time: String = "",  // "HH:mm"
    val kcal: Int = 0,
    val protein: Int = 0,
    val howTo: String = "",   // form cues for exercises ("how to do it")
    val imageUrl: String = "", // animated GIF demo for exercises
    val sortOrder: Int = 0,
    val done: Boolean = false,
)

/** Category drives the icon + accent colour in the UI. Stored as the enum name. */
enum class TaskCategory {
    WALK, LEARN, MEAL, WARMUP, WORKOUT, SPORT, REST, STRETCH;

    companion object {
        fun from(name: String): TaskCategory =
            entries.firstOrNull { it.name == name } ?: MEAL
    }
}
