package gi.zangurashvili.todo.domain

import gi.zangurashvili.todo.data.ListItem
import gi.zangurashvili.todo.data.ToDoListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ToDoListRepository{
    suspend fun getToDoListItems(): List<ListItem>
}

class ToDoListRepositoryImpl(val toDoListDao: ToDoListDao): ToDoListRepository {
    override suspend fun getToDoListItems(): List<ListItem> {
        return withContext(Dispatchers.IO){
            toDoListDao.getListItems()
        }
    }
}