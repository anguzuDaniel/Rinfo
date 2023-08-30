package com.danotech.rinfo.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a category of business in a local sql lite.
 * @param id: the id of the category
 * @param name: the name of the category
 * @return a category
 * @see Category
 */
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String
)