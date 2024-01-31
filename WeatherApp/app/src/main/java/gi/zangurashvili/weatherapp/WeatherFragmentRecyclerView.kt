package gi.zangurashvili.weatherapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gi.zangurashvili.weatherapp.databinding.FirstPageRowItemBinding

class WeatherFragmentRecyclerView(val data: ArrayList<WeatherFragmentRowItem>):
    RecyclerView.Adapter<WeatherFragmentRecyclerView.WeatherFragmentViewHolder>() {

    class WeatherFragmentViewHolder(binding: FirstPageRowItemBinding): RecyclerView.ViewHolder(binding.root){
        val description: TextView = binding.description
        val value: TextView = binding.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  WeatherFragmentViewHolder{
        return WeatherFragmentViewHolder(FirstPageRowItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: WeatherFragmentViewHolder, position: Int) {
        holder.description.text = data[position].description
        holder.value.text = data[position].value
    }

    override fun getItemCount(): Int {
        return data.size
    }
}