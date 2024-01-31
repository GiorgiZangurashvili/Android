package gi.zangurashvili.todo.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import gi.zangurashvili.todo.data.ListItem
import gi.zangurashvili.todo.data.ToDoListDatabase
import gi.zangurashvili.todo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listItemAdapter: ListItemAdapter

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.getViewModelFactory(
            applicationContext
        )
    }

    private val onClickListener = View.OnClickListener{ v ->
        Thread{
            val listItemDao = ToDoListDatabase.getInstance(applicationContext).getToDoListDao()
            var list = ArrayList<String>()
            var isChecked = ArrayList<String>()
            listItemDao.addListItem(ListItem(listName = "", listItems = list, isChecked = isChecked))
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init()
        Thread {
            val listItemDao = ToDoListDatabase.getInstance(applicationContext).getToDoListDao()
            listItemDao.clearDatabase()
        }.start()
    }

    private fun convertDPtoPixels(dpVal: Int): Int{
        val scale = resources.displayMetrics.density
        return (dpVal * scale + 0.5f).toInt()
    }

    private fun init(){
        initRecyclerView()
        initMainViewModel()
        setOnClickListeners()
    }

    private fun initRecyclerView(){
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        listItemAdapter = ListItemAdapter(emptyList())
        recyclerView.adapter = listItemAdapter
        recyclerView.addItemDecoration(ItemDecoration(convertDPtoPixels(8)))
    }

    private fun initMainViewModel(){
        mainViewModel.liveData.observe(this){items ->
            listItemAdapter.items = items
            listItemAdapter.notifyDataSetChanged()
        }
    }

    private fun setOnClickListeners(){
        binding.addButton.setOnClickListener(onClickListener)
    }
}