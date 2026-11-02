package com.fittrack.app.notify

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

/**
 * Schedules one daily exact alarm per routine reminder. Each fired alarm
 * re-schedules itself for the next day from [ReminderReceiver].
 */
object ReminderScheduler {

    data class Reminder(
        val id: Int,
        val hour: Int,
        val minute: Int,
        val title: String,
        val text: String,
    )

    val REMINDERS = listOf(
        Reminder(1, 6, 15, "Before gym 🍌", "1 banana + black coffee, plus water."),
        Reminder(2, 7, 0, "Gym time 💪", "Warm up 5–8 min, then today's session. Sat: outdoor run. Sun: rest."),
        Reminder(3, 8, 30, "After gym 🥤", "1 scoop whey in water. Did you stretch?"),
        Reminder(4, 13, 0, "Lunch 🥗", "Boiled bowl + dal, curd, salad, 2 rotis + 3 eggs."),
        Reminder(5, 16, 0, "4 pm snack", "Banana with oats and seeds."),
        Reminder(6, 18, 0, "Posture work 🧘", "10 min: bridges, dead bugs, bird dogs, plank, wall angels, chin tucks."),
        Reminder(7, 20, 30, "Dinner 🍽️", "2 rotis + dal, egg or paneer + sabzi, whole fruit."),
        Reminder(8, 21, 30, "Soak beans 🫘", "Soak tomorrow's chana/moong/soya. Sleep 7–8 h."),
    )

    fun scheduleAll(context: Context) {
        Notifications.ensureChannel(context)
        REMINDERS.forEach { scheduleOne(context, it) }
    }

    fun cancelAll(context: Context) {
        val am = context.getSystemService(AlarmManager::class.java)
        REMINDERS.forEach { r ->
            am.cancel(pendingIntent(context, r))
        }
    }

    fun scheduleOne(context: Context, reminder: Reminder) {
        val am = context.getSystemService(AlarmManager::class.java)
        val pi = pendingIntent(context, reminder)

        val now = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminder.hour)
            set(Calendar.MINUTE, reminder.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (!after(now)) add(Calendar.DAY_OF_MONTH, 1)
        }

        try {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis, pi)
        } catch (e: SecurityException) {
            // Exact alarms not permitted — fall back to an inexact (still daily) alarm.
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.timeInMillis, pi)
        }
    }

    fun reminderById(id: Int): Reminder? = REMINDERS.firstOrNull { it.id == id }

    private fun pendingIntent(context: Context, reminder: Reminder): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_ID, reminder.id)
            putExtra(EXTRA_TITLE, reminder.title)
            putExtra(EXTRA_TEXT, reminder.text)
        }
        return PendingIntent.getBroadcast(
            context, reminder.id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    const val EXTRA_ID = "extra_id"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_TEXT = "extra_text"
}
