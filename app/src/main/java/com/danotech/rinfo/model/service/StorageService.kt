
package com.danotech.rinfo.model.service

import com.danotech.rinfo.model.Review
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val reviews: Flow<List<Review>>
  suspend fun getReview(reviewId: String): Review?
  suspend fun save(review: Review): String
  suspend fun update(review: Review)
  suspend fun delete(reviewId: String)
}
