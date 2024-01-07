package dev.jmsg.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dev.jmsg.notes.model.entity.Category
import dev.jmsg.notes.model.repository.CategoryRepository

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    val categoryRepository: CategoryRepository

    init {
        categoryRepository = CategoryRepository(application)
    }

    fun getCategories(): LiveData<List<Category>>
    {
        return categoryRepository.getCategories().asLiveData()
    }

    fun getCategoryByName(name: String): LiveData<List<Category>>
    {
        return categoryRepository.getCategoryByName(name).asLiveData()
    }

    fun insertCategory(category: Category)
    {
        return categoryRepository.insertCategory(category)
    }

    fun updateCategory(category: Category)
    {
        return categoryRepository.updateCategory(category)
    }

    fun deleteCategory(category: Category)
    {
        return categoryRepository.deleteCategory(category)
    }
}