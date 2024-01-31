package gi.zangurashvili.alarmapp.data

import com.google.gson.Gson
import android.content.Context
import com.google.gson.reflect.TypeToken
import gi.zangurashvili.alarmapp.presentation.MainActivity

class AlarmData(private val activity: MainActivity) {

    fun addAlarm(alarmItem: AlarmItem){
        val alarms = getAlarms()
        alarms.add(alarmItem)

        val alarmsAsJson = Gson().toJson(alarms)

        val sharedPreferences = activity.getSharedPreferences("AlarmData", Context.MODE_PRIVATE)
        val editSharedPreferences = sharedPreferences.edit()
        editSharedPreferences.putString("alarms", alarmsAsJson)
        editSharedPreferences.apply()
    }

    fun getAlarms(): MutableList<AlarmItem>{
        val sharedPreferences = activity.getSharedPreferences("AlarmData", Context.MODE_PRIVATE)
        val alarmsAsJson = sharedPreferences.getString("alarms", null)

        val type = object : TypeToken<MutableList<AlarmItem>>() {}.type

        val alarms = Gson().fromJson<MutableList<AlarmItem>>(alarmsAsJson, type)
        if (alarms == null){
            return mutableListOf()
        }
        return alarms
    }
}