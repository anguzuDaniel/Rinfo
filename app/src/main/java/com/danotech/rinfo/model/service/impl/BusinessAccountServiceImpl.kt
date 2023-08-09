package com.danotech.rinfo.model.service.impl

import com.danotech.rinfo.model.BusinessDocument
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.trace
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BusinessAccountServiceImpl
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountService
) : BusinessAccountService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val businessAccount: Flow<List<BusinessDocument>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                fireStore.collection(PROFILE_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getBusiness(businessId: String): BusinessDocument? =
        fireStore.collection(PROFILE_COLLECTION).document(businessId).get().await().toObject()

    override suspend fun create(businessDocument: BusinessDocument): String =
        trace(SAVE_PROFILE_TRACE) {
            val businessWithUserId =
                businessDocument.copy(id = FirebaseAuth.getInstance().currentUser!!.email.toString())
            fireStore.collection(PROFILE_COLLECTION).document(businessWithUserId.id)
                .set(businessWithUserId).await().toString()
        }

    override suspend fun update(businessDocument: BusinessDocument): Unit =
        trace(UPDATE_PROFILE_TRACE) {
            val businessId = businessDocument.id
            fireStore.collection(PROFILE_COLLECTION).document(businessId).set(businessDocument).await()
        }

    override suspend fun delete(business: String) {
        fireStore.collection(PROFILE_COLLECTION).document(business).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val PROFILE_COLLECTION = "businessAccount"
        private const val SAVE_PROFILE_TRACE = "saveBusinessAccount"
        private const val UPDATE_PROFILE_TRACE = "updateBusinessAccount"
    }
}