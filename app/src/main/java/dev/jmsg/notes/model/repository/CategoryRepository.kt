package dev.jmsg.notes.model.repository

import android.content.Context
import dev.jmsg.notes.model.dao.CategoryDao
import dev.jmsg.notes.model.database.NoteDatabase
import dev.jmsg.notes.model.entity.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(context: Context) {

    val categoryDao:CategoryDao
    private lateinit var liveCategories: Flow<List<Category>>
    private lateinit var liveDataCategory: Flow<List<Category>>

    init {
        val db : NoteDatabase = NoteDatabase.getDatabase(context)
        categoryDao = db.getCategoryDao()
    }

    fun getCategories():Flow<List<Category>>{
        liveCategories = categoryDao.getCategories()
        return liveCategories
    }

    fun getCategoryByName(name:String): Flow<List<Category>>
    {
        liveDataCategory = categoryDao.getCategoryByName(name)
        return liveDataCategory
    }

    fun insertCategory(category: Category)
    {
        Thread{
            categoryDao.insertCategory(category)
        }.start()
    }

    fun deleteCategory(category: Category){
        Thread{
            categoryDao.deleteCategory(category)
        }.start()
    }

    fun updateCategory(category: Category) {
        Thread{
            categoryDao.updateCategory(category)
        }.start()
    }
}