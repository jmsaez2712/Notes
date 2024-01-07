package dev.jmsg.notes.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.ForeignKey.CASCADE
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.jmsg.notes.model.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = CASCADE)
    fun insertCategory(category: Category): Long

    @Update
    fun updateCategory(category:Category): Int

    @Query("SELECT * FROM categories ORDER BY name DESC")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE name = :name")
    fun getCategoryByName(name: String): Flow<List<Category>>

    @Delete
    fun deleteCategory(category: Category): Int
}