
package com.danotech.rinfo.model.service.impl

import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.StorageService
import com.danotech.rinfo.model.service.trace
import com.danotech.rinfo.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
  StorageService {

  @OptIn(ExperimentalCoroutinesApi::class)
  override val reviews: Flow<List<Review>>
    get() =
      auth.currentUser.flatMapLatest { user ->
        firestore.collection(REVIEW_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
      }

  override suspend fun getReview(reviewId: String): Review? =
    firestore.collection(REVIEW_COLLECTION).document(reviewId).get().await().toObject()

  override suspend fun save(review: Review): String =
    trace(SAVE_TASK_TRACE) {
      val reviewWithUserId = review.copy(id = auth.currentUserId)
      firestore.collection(REVIEW_COLLECTION).add(reviewWithUserId).await().id
    }

  override suspend fun update(review: Review): Unit =
    trace(UPDATE_TASK_TRACE) {
      firestore.collection(REVIEW_COLLECTION).document(review.id).set(review).await()
    }

  override suspend fun delete(reviewId: String) {
    firestore.collection(REVIEW_COLLECTION).document(reviewId).delete().await()
  }

  companion object {
    private const val USER_ID_FIELD = "userId"
    private const val REVIEW_COLLECTION = "reviews"
    private const val SAVE_TASK_TRACE = "saveReview"
    private const val UPDATE_TASK_TRACE = "updateReview"
  }
}
