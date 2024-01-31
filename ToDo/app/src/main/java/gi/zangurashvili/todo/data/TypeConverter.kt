package gi.zangurashvili.todo.data

import androidx.room.TypeConverter

class TypeConverter {
    @TypeConverter
    fun fromString(listAsString: String): List<String> {
        return listAsString.split(",")
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(",")
    }
}