package com.danotech.rinfo.model

import com.google.firebase.firestore.DocumentId

/**
 *  Represents a review in the Firebase document
 *  @param id: the id of the review
 *  @param userImageUrl: the url of the image
 *  @param avatarResource: the resource of the avatar
 *  @param title: the name of the business
 *  @param rating: the rating of the business
 *  @param name the users name
 *  @param review: the comment of the business
 *  @return a review
 *  @see Review
 */
data class Review(
    @DocumentId val id: String = "",
    // to be changed to receive url links
    val userImageUrl: String = "",
    val reviewedBusinessId: String = "",
    val reviewerUserId: String = "",
    val title: String = "",
    val rating: Int = 0,
    val name: String = "",
    val review: String = "",
    var postive: Boolean = false,
    var date: String = "",
    var edited: Boolean = false
)
