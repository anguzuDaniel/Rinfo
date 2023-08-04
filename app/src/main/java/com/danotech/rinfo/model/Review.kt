package com.danotech.rinfo.model

import com.google.firebase.firestore.DocumentId

data class Review(
    @DocumentId val id: String = "",
    // to be changed to receive url links
    val imageUrl: String = "",
    val avatarResource: Int,
    val businessName: String = "",
    val rating: Int = 0,
    val comment: String = ""
)
