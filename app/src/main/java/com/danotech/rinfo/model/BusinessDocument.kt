package com.danotech.rinfo.model

import com.danotech.rinfo.model.local.Category
import com.google.firebase.firestore.DocumentId

/**
 * Represents a business in a remote firestore database.
 * @param id: the id of the business
 * @param userId: the id of the user that owns the business
 * @param name: the name of the business
 * @param description: the description of the business
 * @param address: the address of the business
 * @param phone: the phone of the business
 * @param email: the email of the business
 * @param businessCategory: the category of the business
 * @param reviews: the number of reviews of the business
 * @return a business
 * @see BusinessDocument
 * @see Category
 * @see DocumentId
 */
data class BusinessDocument(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val businessCategory: String = Category(name = "").name,
    val reviews: Int = 0,
)
