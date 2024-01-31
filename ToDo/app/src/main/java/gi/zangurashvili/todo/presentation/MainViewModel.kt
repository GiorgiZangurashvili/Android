package gi.zangurashvili.todo.presentation

import android.content.Context
import androidx.lifecycle.*
import gi.zangurashvili.todo.data.ListItem
import gi.zangurashvili.todo.data.ToDoListDatabase
import gi.zangurashvili.todo.domain.ToDoListRepositoryImpl
import gi.zangurashvili.todo.domain.ToDoListRepository
import kotlinx.coroutines.launch

class MainViewModel(private val todoListRepo: ToDoListRepository): ViewModel() {
    private var _liveData = MutableLiveData<List<ListItem>>()
    val liveData: LiveData<List<ListItem>> get() = _liveData

    init {
        viewModelScope.launch {
            val list = todoListRepo.getToDoListItems()
            _liveData.value = list
        }
    }

    companion object{
        fun getViewModelFactory(context: Context): MainViewModelFactory{
            return MainViewModelFactory(context)
        }
    }

    class MainViewModelFactory(val context: Context): ViewModelProvider.Factory{
        override fun<T : ViewModel> create(modelClass: Class<T>): T{
            return MainViewModel(
                ToDoListRepositoryImpl(
                    ToDoListDatabase.getInstance(context).getToDoListDao()
                )
            ) as T
        }
    }
}