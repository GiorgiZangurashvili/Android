package gi.zangurashvili.alarmapp.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gi.zangurashvili.alarmapp.R
import gi.zangurashvili.alarmapp.data.AlarmData
import gi.zangurashvili.alarmapp.data.AlarmItem
import gi.zangurashvili.alarmapp.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnTimeSetListener {
    private lateinit var binding: ActivityMainBinding
    private var currentColor: String = "white"
    private lateinit var alarmData: AlarmData
    private lateinit var recyclerView: RecyclerView
    private var pendingIntentValue: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmData = AlarmData(this)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AlarmItemAdapter(alarmData.getAlarms() as ArrayList<AlarmItem>, "white")

        binding.addButton.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, null)
        }

        binding.switchTheme.setOnClickListener {
            if (binding.switchTheme.text.equals("Switch to light")) {
                binding.topPanel.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.switchTheme.text = getString(R.string.switch_dark)
                binding.switchTheme.setTextColor(ContextCompat.getColor(this, R.color.black))
                val alarms: ArrayList<AlarmItem> = alarmData.getAlarms() as ArrayList<AlarmItem>
                val newAdapter = AlarmItemAdapter(alarms, "white")
                recyclerView.adapter = newAdapter
                newAdapter.notifyDataSetChanged()
                currentColor = "white"
            } else {
                binding.topPanel.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                binding.switchTheme.text = getString(R.string.switch_light)
                binding.switchTheme.setTextColor(ContextCompat.getColor(this, R.color.white))
                val alarms: ArrayList<AlarmItem> = alarmData.getAlarms() as ArrayList<AlarmItem>
                val newAdapter = AlarmItemAdapter(alarms, "gray")
                recyclerView.adapter = newAdapter
                newAdapter.notifyDataSetChanged()
                currentColor = "gray"
            }
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var hour: String = "" + hourOfDay
        var min: String = "" + minute
        if (hourOfDay >= 0 && hourOfDay < 10){
            hour = "0$hour"
        }
        if (minute >= 0 && minute < 10){
            min = "0$min"
        }
        val alarmTime: String = String.format("%s:%s", hour, min)
        val alarmItem = AlarmItem(alarmTime, true)
        alarmData.addAlarm(alarmItem)
        recyclerView.adapter = AlarmItemAdapter(alarmData.getAlarms() as ArrayList<AlarmItem>, currentColor)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        setAlarm(calendar)
    }

    fun setAlarm(calendar: Calendar){
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent: Intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("alarmHour", calendar.get(Calendar.HOUR_OF_DAY))
        intent.putExtra("alarmMinute", calendar.get(Calendar.MINUTE))
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, pendingIntentValue, intent, 0)
        pendingIntentValue++

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}