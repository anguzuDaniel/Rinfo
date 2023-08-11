package com.danotech.rinfo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danotech.rinfo.model.local.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): Flow<Category>

    @Query("SELECT * FROM categories WHERE name LIKE :name || '%'")
    fun getCategoryByName(name: String): Flow<List<Category>>
}