package gi.zangurashvili.todo.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gi.zangurashvili.todo.data.ListItem
import gi.zangurashvili.todo.data.ToDoListDatabase
import gi.zangurashvili.todo.databinding.ActivityMainBinding
import gi.zangurashvili.todo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listItem: ListItem
    private lateinit var adapter: SecondPageItemAdapter
//    private val launcher = registerForActivityResult(Activi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        initListItem()
        initRecyclerView()
        setOnClickListeners()
    }

    private fun initListItem(){
        val listItemAsJson = intent.getStringExtra("ListItemAsJson")
        val gsonInstance = Gson()
        listItem = gsonInstance.fromJson(listItemAsJson, ListItem::class.java)
        binding.editText.setText(listItem.listName)
    }

    private fun initRecyclerView(){
        recyclerView = binding.secondRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SecondPageItemAdapter(listItem)
        recyclerView.adapter = adapter
    }

    private fun setOnClickListeners(){
        binding.add.setOnClickListener(addNewItemListener)
        binding.addListItem.setOnClickListener(addNewItemListener)
        binding.goBack.setOnClickListener(addNewItemListener)
    }

    private val addNewItemListener = View.OnClickListener{ v ->
        val newList = ArrayList<String>()
        val newIsCheckedList = ArrayList<String>()

        for (i in 0 until adapter.itemCount){
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? SecondPageItemAdapter.SecondPageViewHolder
            if (viewHolder != null){
                newList[i] = viewHolder.checkboxDesc.text.toString()
                if (viewHolder.checkbox.isChecked){
                    newIsCheckedList[i] = "True"
                }else{
                    newIsCheckedList[i] = "False"
                }
            }
        }

        adapter.item.listItems = newList
        adapter.item.isChecked = newIsCheckedList

        Thread{
            val listItemDao = ToDoListDatabase.getInstance(applicationContext).getToDoListDao()
            listItemDao.updateListItem(listItem)
        }.start()
        if (v.id == binding.add.id || v.id == binding.addListItem.id){
            adapter.notifyDataSetChanged()
        }
        if (v.id == binding.goBack.id){
            val intent = Intent(this, MainActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
    }
}