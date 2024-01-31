package gi.zangurashvili.alarmapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import gi.zangurashvili.alarmapp.R
import gi.zangurashvili.alarmapp.data.AlarmItem
import gi.zangurashvili.alarmapp.databinding.ItemBinding

class AlarmItemAdapter(private val items: ArrayList<AlarmItem>, var color: String): RecyclerView.Adapter<AlarmItemAdapter.AlarmItemViewHolder>() {

    class AlarmItemViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        val alarmTime: TextView = binding.alarmTime
        val alarmSwitch: Switch = binding.alarmSwitch
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmItemViewHolder {
        return AlarmItemViewHolder(ItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
        holder.alarmTime.text = items[position].time
        holder.alarmSwitch.isChecked = items[position].switched
        if (color == "gray"){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
            holder.alarmTime.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.alarmTime.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
    }
}