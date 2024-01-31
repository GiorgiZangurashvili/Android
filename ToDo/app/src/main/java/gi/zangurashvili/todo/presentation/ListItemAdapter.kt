package gi.zangurashvili.todo.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gi.zangurashvili.todo.data.ListItem
import gi.zangurashvili.todo.data.TypeConverter
import gi.zangurashvili.todo.databinding.ItemBinding

class ListItemAdapter(var items: List<ListItem>): RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder>() {
    inner class ListItemViewHolder(binding: ItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION){
                    val intent = Intent(binding.root.context, SecondActivity::class.java)
                    val gsonInstance = Gson()
                    val asJson = gsonInstance.toJson(items[adapterPosition])
                    intent.putExtra("ListItemAsJson", asJson)
                    startActivity(binding.root.context, intent, null)
                }
            }
        }

        val title = binding.title
        val checkbox1 = binding.checkbox1
        val checkbox2 = binding.checkbox2
        val checkbox3 = binding.checkbox3
        val dots = binding.dots
        val info = binding.info
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemAdapter.ListItemViewHolder {
        return ListItemViewHolder(ItemBinding
            .inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListItemAdapter.ListItemViewHolder, position: Int) {
        holder.title.text = items[position].listName
        val itemList = items[position].listItems
        val isCheckedList = items[position].isChecked

        var count = 0
        for (item in itemList){
            if(count == 3)
                break
            if(count == 0){
                holder.checkbox1.visibility = View.VISIBLE
                holder.checkbox1.text = itemList[count]
                if (isCheckedList[count] == "True"){
                    holder.checkbox1.isChecked = true
                }
            }else if(count == 1){
                holder.checkbox2.visibility = View.VISIBLE
                holder.checkbox2.text = itemList[count]
                if (isCheckedList[count] == "True"){
                    holder.checkbox2.isChecked = true
                }
            }else if(count == 2){
                holder.checkbox3.visibility = View.VISIBLE
                holder.checkbox3.text = itemList[count]
                if (isCheckedList[count] == "True"){
                    holder.checkbox3.isChecked = true
                }
            }
            count++
        }

        if(itemList.size > 3){
            holder.dots.visibility = View.VISIBLE
            holder.info.visibility = View.VISIBLE
            holder.info.text = String.format("+ %d checked items", itemList.size - 3)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}