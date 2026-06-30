package com.fittrack.app.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Fires at a reminder's time: posts the notification and re-schedules for tomorrow. */
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(ReminderScheduler.EXTRA_ID, 0)
        val title = intent.getStringExtra(ReminderScheduler.EXTRA_TITLE) ?: "FitTrack"
        val text = intent.getStringExtra(ReminderScheduler.EXTRA_TEXT) ?: ""

        Notifications.show(context, id, title, text)

        // Re-arm the same reminder for the next day.
        ReminderScheduler.reminderById(id)?.let {
            ReminderScheduler.scheduleOne(context, it)
        }
    }
}
