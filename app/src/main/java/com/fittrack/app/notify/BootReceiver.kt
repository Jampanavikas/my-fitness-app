package com.fittrack.app.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/** Re-schedules all reminders after the device reboots (alarms are cleared on boot). */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            ReminderScheduler.scheduleAll(context)
        }
    }
}
