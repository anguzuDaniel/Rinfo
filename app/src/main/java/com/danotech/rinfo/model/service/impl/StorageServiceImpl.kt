/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

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
        firestore.collection(TASK_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
      }

  override suspend fun getReview(reviewId: String): Review? =
    firestore.collection(TASK_COLLECTION).document(reviewId).get().await().toObject()

  override suspend fun save(review: Review): String =
    trace(SAVE_TASK_TRACE) {
      val taskWithUserId = review.copy(id = auth.currentUserId)
      firestore.collection(TASK_COLLECTION).add(taskWithUserId).await().id
    }

  override suspend fun update(review: Review): Unit =
    trace(UPDATE_TASK_TRACE) {
      firestore.collection(TASK_COLLECTION).document(review.id).set(review).await()
    }

  override suspend fun delete(reviewId: String) {
    firestore.collection(TASK_COLLECTION).document(reviewId).delete().await()
  }

  companion object {
    private const val USER_ID_FIELD = "userId"
    private const val TASK_COLLECTION = "reviews"
    private const val SAVE_TASK_TRACE = "saveReview"
    private const val UPDATE_TASK_TRACE = "updateReview"
  }
}
