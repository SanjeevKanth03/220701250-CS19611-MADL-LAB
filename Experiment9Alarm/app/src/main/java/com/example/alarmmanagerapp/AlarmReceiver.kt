package com.example.alarmmanagerapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.widget.Toast
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val r = RingtoneManager.getRingtone(context, ringtone)
        r.play()

        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_LONG).show()

        // Snooze after 10 minutes
        val snoozeIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val snoozeTime = System.currentTimeMillis() + 10 * 60 * 1000  // 10 minutes
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, snoozeTime, pendingIntent)
    }
}
