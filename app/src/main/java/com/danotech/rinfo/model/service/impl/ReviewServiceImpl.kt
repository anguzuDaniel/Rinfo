package com.danotech.rinfo.model.service.impl

import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.components.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewServiceImpl
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountService
) : ReviewService {
    override val reviews: List<Review>
        get() = fireStore.collection(REVIEW_COLLECTION).get().result!!.toObjects(Review::class.java)

    override suspend fun getAllReviews(): Flow<List<Review>> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewsByBusinessId(businessId: String): Flow<List<Review>> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewsByUserId(userId: String): Flow<List<Review>> {
        TODO("Not yet implemented")
    }

    override suspend fun create(review: Review): String {
        TODO("Not yet implemented")
    }

    override suspend fun update(review: Review) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(reviewId: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val REVIEW_FIELD = "review"
        private const val USER_ID_FIELD = "userId"
        private const val REVIEW_COLLECTION = "reviews"
        private const val SAVE_REVIEW_TRACE = "saveReview"
        private const val UPDATE_REVIEW_TRACE = "updateReview"
    }
}