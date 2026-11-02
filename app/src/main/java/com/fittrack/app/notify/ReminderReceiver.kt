package com.fittrack.app.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Fires at a reminder's time: posts the notification and re-schedules for tomorrow. */
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Look up the current text (and drop alarms from an older plan).
        val r = ReminderScheduler.reminderById(intent.getIntExtra(ReminderScheduler.EXTRA_ID, 0)) ?: return
        Notifications.show(context, r.id, r.title, r.text)
        ReminderScheduler.scheduleOne(context, r) // re-arm for tomorrow
    }
}
