package com.danotech.rinfo.model.service.impl

import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.model.service.trace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewServiceImpl
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
) : ReviewService {

    // Declare a MutableStateFlow to hold the list of reviews.
    private val reviewsFlow = MutableStateFlow<List<Review>>(emptyList())

    override suspend fun getAllReviews(): Flow<List<Review>> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewById(reviewId: String): Review {
        return fireStore.collection(REVIEW_COLLECTION).document(reviewId).get().await()
            .toObject(Review::class.java)!!
    }

    // Create a function to start listening to reviews by businessId.
    override fun startListeningToReviewsByBusinessId(businessId: String) {
        val query = fireStore.collection(REVIEW_COLLECTION)
            .whereEqualTo(BUSINESS_ID_FIELD, businessId)

        val registration = query.addSnapshotListener { querySnapshot, exception ->
            if (exception != null) {
                // Handle any errors here.
                // You can log the error or emit an error state in your app as needed.
                return@addSnapshotListener
            }

            val reviews = querySnapshot?.documents?.mapNotNull { document ->
                document.toObject(Review::class.java)
            } ?: emptyList()

            // Update the MutableStateFlow with the new list of reviews.
            reviewsFlow.value = reviews
        }

        // Store the listener registration so that you can remove it when necessary.
        // Make sure to remove the listener when it's no longer needed.
        // For example, in your ViewModel's onCleared() method.
         registration.remove()
    }

    // Use this function to get the Flow of reviews.
    fun getReviewsFlow(): Flow<List<Review>> {
        return reviewsFlow
    }

    override suspend fun getReviewsByUserId(userId: String): Flow<List<Review>> {
        return fireStore.collection(REVIEW_COLLECTION).whereEqualTo(USER_ID_FIELD, userId)
            .dataObjects()
    }

    override suspend fun create(review: Review): String =
        trace(SAVE_REVIEW_TRACE) {
            fireStore.collection(REVIEW_COLLECTION).document().set(review).await().toString()
        }

    override suspend fun update(review: Review) {
        trace(UPDATE_REVIEW_TRACE) {
            fireStore.collection(REVIEW_COLLECTION).document(review.id).set(review).await()
        }
    }

    override suspend fun delete(reviewId: String) {
        trace(UPDATE_REVIEW_TRACE) {
            fireStore.collection(REVIEW_COLLECTION).document(reviewId).delete().await()
        }
    }

    companion object {
        private const val REVIEW_FIELD = "review"
        private const val USER_ID_FIELD = "reviewerUserId"
        private const val BUSINESS_ID_FIELD = "reviewedBusinessId"
        private const val REVIEW_COLLECTION = "reviews"
        private const val SAVE_REVIEW_TRACE = "saveReview"
        private const val UPDATE_REVIEW_TRACE = "updateReview"
    }
}