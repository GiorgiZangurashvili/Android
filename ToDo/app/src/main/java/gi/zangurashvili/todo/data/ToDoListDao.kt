package gi.zangurashvili.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoListDao {

    @Query("SELECT * FROM ListItem")
    fun getListItems(): List<ListItem>

    @Insert
    fun addListItem(listItem: ListItem)

    @Update
    fun updateListItem(listItem: ListItem)

    @Query("DELETE FROM ListItem")
    fun clearDatabase()
}