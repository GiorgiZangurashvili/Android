package gi.zangurashvili.weatherapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gi.zangurashvili.weatherapp.databinding.SecondPageRowItemBinding

class ForecastFragmentRecyclerView(val data: ArrayList<ForecastFragmentRowItem>):
    RecyclerView.Adapter<ForecastFragmentRecyclerView.ForecastFragmentViewHolder>() {

    class ForecastFragmentViewHolder(binding: SecondPageRowItemBinding): RecyclerView.ViewHolder(binding.root){
        val date: TextView = binding.date
        val img: ImageView = binding.iconView
        val temp: TextView = binding.temperature
        val description: TextView = binding.description1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastFragmentViewHolder {
        return ForecastFragmentViewHolder(SecondPageRowItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ForecastFragmentViewHolder, position: Int) {
        holder.date.text = data[position].date
        Glide.with(holder.itemView.context).load(data[position].icon).into(holder.img)
        holder.temp.text = data[position].temperature
        holder.description.text = data[position].description
    }
}