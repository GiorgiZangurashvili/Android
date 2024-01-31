package gi.zangurashvili.todo.data

import android.content.Context
import androidx.room.*

@TypeConverters(TypeConverter::class)
@Database(entities = [ListItem::class], version = 1)
abstract class ToDoListDatabase : RoomDatabase() {
    abstract fun getToDoListDao(): ToDoListDao

    companion object{
        @Volatile
        private var instance: ToDoListDatabase? = null

        fun getInstance(context: Context):ToDoListDatabase{
            synchronized(this){
                if(instance == null){
                    instance = Room
                        .databaseBuilder(context = context, ToDoListDatabase::class.java, "todo-list-db")
                        .build()
                }
            }
            return instance!!
        }
    }
}