package dev.jmsg.notes.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "categories",
    indices = [Index(value = ["name"])]
    )
data class Category (
    @PrimaryKey(autoGenerate=true)
    val id: Int? = 0,

    @ColumnInfo(name = "name")
    val name: String?
)
{
    override fun toString(): String
    {
        if (name != null) return name else return ""
    }
}