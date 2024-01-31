package gi.zangurashvili.alarmapp.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import gi.zangurashvili.alarmapp.R

@RequiresApi(Build.VERSION_CODES.O)
class NotificationHandler(base: Context?) : ContextWrapper(base) {

    val CHANNEL_ID = "channel-id"
    val CHANNEL_NAME = "channel-name"
    private lateinit var notificationManager: NotificationManager

    init{
        createChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(){
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lightColor = Color.BLACK
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getNotificationManager().createNotificationChannel(channel)
    }

    fun getNotificationManager(): NotificationManager{
        if (!::notificationManager.isInitialized) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager
    }

    fun getNotification(title: String, msg: String): NotificationCompat.Builder{
        return NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setSmallIcon(R.drawable.alarm_clock)
    }
}