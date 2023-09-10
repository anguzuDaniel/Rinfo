package com.danotech.rinfo.model.service.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.model.service.trace
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.dataObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewServiceImpl
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
) : ReviewService {

    // Declare a MutableStateFlow to hold the list of reviews.
    private val reviewsFlow = MutableStateFlow<List<Review>>(emptyList())
    private var registration: ListenerRegistration? = null

    override suspend fun getAllReviews(businessId: String): Flow<List<Review>> {
        return flow {
            // Fetch the reviews from your data source, e.g., Firestore
            val reviews = fetchReviewsFromDataSource(businessId)
            emit(reviews)
        }
    }

    private suspend fun fetchReviewsFromDataSource(businessId: String): List<Review> {
        // Assuming you have a reference to your Firestore collection
        val collectionReference = FirebaseFirestore.getInstance().collection("reviews")
            .whereEqualTo("reviewedBusinessId", businessId)

        try {
            val querySnapshot = collectionReference.get().await()

            // Map the query snapshot to a list of Review objects
            return querySnapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(Review::class.java)
            }
        } catch (e: Exception) {
            // Handle any exceptions that may occur during data retrieval
            // For example, log the error or throw a custom exception
            throw e
        }
    }

    override suspend fun getReviewById(reviewId: String): Review {
        return fireStore.collection("reviews").document(reviewId).get().await()
            .toObject(Review::class.java)!!
    }

    // Create a function to start listening to reviews by businessId.
    override suspend fun startListeningToReviewsByBusinessId(businessId: String): Flow<List<Review>> {
        return callbackFlow {
            val reviews = ArrayList<Review>()

            val listener = fireStore.collection("reviews")
                .whereEqualTo("reviewedBusinessId", businessId)
                .addSnapshotListener { snapshots, exception ->
                    if (exception != null) {
                        // Handle any errors here.
                        // You can log the error or emit an error state in your app as needed.
                        Log.d(TAG, "listen:error reviews listener", exception)
                        close(exception) // Close the flow with an error if there's an exception.
                        return@addSnapshotListener
                    }

                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> reviews.add(dc.document.toObject(Review::class.java))
                            DocumentChange.Type.MODIFIED -> reviews.add(dc.document.toObject(Review::class.java))
                            DocumentChange.Type.REMOVED -> reviews.add(dc.document.toObject(Review::class.java))
                        }

                        Log.d(TAG, "Downloaded from Document: ${dc.document.toObject(Review::class.java)}")
                    }

                    // Emit the updated list of reviews
                    trySend(reviews.toList())
                }

            // Close the listener when the flow is cancelled (e.g., ViewModel's onCleared())
            awaitClose {
                listener.remove()
            }
        }.flowOn(Dispatchers.IO)
    }

    // Remove the listener when it's no longer needed, for example, in onCleared().
    fun removeListener() {
        registration?.remove()
    }

    // Use this function to get the Flow of reviews.
    override suspend fun getReviewsFlow(): Flow<List<Review>> {
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