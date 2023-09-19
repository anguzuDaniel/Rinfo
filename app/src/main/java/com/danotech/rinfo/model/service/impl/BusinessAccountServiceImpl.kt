package com.danotech.rinfo.model.service.impl

import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.trace
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BusinessAccountServiceImpl
@Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountService
) : BusinessAccountService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentUserBusinessAccount: Flow<List<Business>>
        get() = auth.currentUser.flatMapLatest { user ->
            fireStore.collection(BUSINESS_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                .dataObjects()
        }

    override suspend fun getAllBusiness(number: Int): Flow<List<Business>> {
        return fireStore.collection(BUSINESS_COLLECTION).limit(number.toLong()).dataObjects()
    }

    override suspend fun getBusinessByCategory(category: String): Flow<List<Business>> {
        return fireStore.collection(BUSINESS_COLLECTION).whereEqualTo(CATEGORY_FIELD, category)
            .dataObjects()
    }

    override suspend fun getBusinessWhereLike(name: String): Flow<List<Business>> =
        fireStore.collection(BUSINESS_COLLECTION).whereEqualTo(BUSINESS_NAME, name).dataObjects()


    override suspend fun getBusinessById(businessId: String): Business? =
        fireStore.collection(BUSINESS_COLLECTION)
            .document(businessId)
            .get()
            .await()
            .toObject(Business::class.java)


    override suspend fun getBusinessByOwner(owner: String): Flow<List<Business>> {
        TODO("Not yet implemented")
    }

    override suspend fun create(business: Business): String = trace(SAVE_BUSINESS_TRACE) {
        try {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val businessWithUserId = business.copy(id = currentUser.email.toString())
                val documentReference =
                    fireStore.collection(BUSINESS_COLLECTION).document(businessWithUserId.id)
                documentReference.set(businessWithUserId).await()
                return documentReference.id // Return the document ID if needed
            } else {
                // Handle the case where there is no signed-in user
                return ""
            }
        } catch (e: Exception) {
            // Handle any exceptions that may occur during Firestore write
            // You can log the error or provide feedback to the user
            return ""
        }
    }

    override suspend fun update(business: Business): Unit = trace(UPDATE_BUSINESS_TRACE) {
        // get id of the current business before use
        val businessId = business.userId

        // add it to the database
        fireStore.collection(BUSINESS_COLLECTION).document(businessId).set(business).await()
    }

    override suspend fun delete(business: String) {
        fireStore.collection(BUSINESS_COLLECTION).document(business).delete().await()
    }

    /**
     * Uploads images to firebase store
     * @param businessId
     * @param image
     */
    override suspend fun upLoadImage(businessId: String, image: String) {
        fireStore.collection(BUSINESS_COLLECTION).document(businessId)
            .update(BUSINESS_LOGO_FILED, image)
            .await()
    }

    companion object {
        private const val CATEGORY_FIELD = "category"
        private const val USER_ID_FIELD = "userId"
        private const val BUSINESS_LOGO_FILED = "logo"
        private const val BUSINESS_NAME = "name"
        private const val BUSINESS_COLLECTION = "businessAccount"
        private const val SAVE_BUSINESS_TRACE = "saveBusinessAccount"
        private const val UPDATE_BUSINESS_TRACE = "updateBusinessAccount"
    }
}