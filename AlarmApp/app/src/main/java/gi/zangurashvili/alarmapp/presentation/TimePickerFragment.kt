package gi.zangurashvili.alarmapp.presentation

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.DateFormat

class TimePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(requireActivity(), requireActivity() as TimePickerDialog.OnTimeSetListener, hour, minute, true)
    }
}