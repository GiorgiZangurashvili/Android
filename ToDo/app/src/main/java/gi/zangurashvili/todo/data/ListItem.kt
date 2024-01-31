package gi.zangurashvili.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val listName: String,
    @ColumnInfo var listItems: List<String>,
    @ColumnInfo var isChecked: List<String>
//    @ColumnInfo val isPinned: Boolean = false
)

