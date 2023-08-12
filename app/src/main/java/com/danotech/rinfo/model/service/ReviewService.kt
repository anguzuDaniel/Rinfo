package com.danotech.rinfo.model.service

import com.danotech.rinfo.ui.components.Review
import kotlinx.coroutines.flow.Flow

/**
 *  Represents a review service
 *  @return a review service
 *  @see ReviewService
 *  @see Review
 *  @see Flow
 *  @see List
 *  @see String
 *  @see suspend
 *  @see getAllReviews
 *  @see getReviewsByBusinessId
 *  @see getReviewsByUserId
 *  @see create
 *  @see update
 *  @see delete
 *  @see reviews
 *  @see Review
 */
interface ReviewService {
    val reviews: List<Review>
    suspend fun getAllReviews(): Flow<List<Review>>
    suspend fun getReviewsByBusinessId(businessId: String): Flow<List<Review>>
    suspend fun getReviewsByUserId(userId: String): Flow<List<Review>>
    suspend fun create(review: Review): String
    suspend fun update(review: Review)
    suspend fun delete(reviewId: String)
}