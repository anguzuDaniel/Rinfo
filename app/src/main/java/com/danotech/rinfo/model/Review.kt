package com.danotech.rinfo.model

import com.google.firebase.firestore.DocumentId

/**
 *  Represents a review in the Firebase document
 *  @param id: the id of the review
 *  @param imageUrl: the url of the image
 *  @param avatarResource: the resource of the avatar
 *  @param title: the name of the business
 *  @param rating: the rating of the business
 *  @param review: the comment of the business
 *  @return a review
 *  @see Review
 */
data class Review(
    @DocumentId val id: String = "",
    // to be changed to receive url links
    val imageUrl: String = "",
    val reviewedBusinessId: String = "",
    val reviewerUserId: String = "",
    val title: String = "",
    val rating: Int = 0,
    val review: String = "",
    var postive: Boolean = false,
    var date: String = "",
    var edited: Boolean = false
)
