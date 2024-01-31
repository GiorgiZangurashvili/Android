package gi.zangurashvili.todo.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gi.zangurashvili.todo.data.ListItem
import gi.zangurashvili.todo.databinding.SecondPageItemBinding

class SecondPageItemAdapter(var item: ListItem) : RecyclerView.Adapter<SecondPageItemAdapter.SecondPageViewHolder>() {
    class SecondPageViewHolder(binding: SecondPageItemBinding): RecyclerView.ViewHolder(binding.root){
        val checkbox = binding.checkbox
        val closeImageButton = binding.deleteButton
        val checkboxDesc = binding.checkboxDesc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondPageViewHolder {
        return SecondPageViewHolder(
            SecondPageItemBinding
                .inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: SecondPageViewHolder, position: Int) {
        if (item.isChecked[position] == "True"){
            holder.checkbox.isChecked = true
            holder.closeImageButton.visibility = View.VISIBLE
        }
        holder.checkboxDesc.setText(item.listItems[position])
    }

    override fun getItemCount(): Int {
        return item.listItems.size
    }
}