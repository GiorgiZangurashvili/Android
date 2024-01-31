package gi.zangurashvili.alarmapp.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmHour = intent?.getIntExtra("alarmHour", 0) ?: 0
        val alarmMinute = intent?.getIntExtra("alarmMinute", 0) ?: 0
        var notificationHandler: NotificationHandler = NotificationHandler(context)
        notificationHandler.getNotificationManager()
            .notify(1, notificationHandler
                .getNotification("Alarm message!", String.format("Alarm set on %d:%d", alarmHour, alarmMinute))
                .build())
    }
}