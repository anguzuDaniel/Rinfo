package com.danotech.rinfo.model.service.impl

import android.graphics.Bitmap
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.trace
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
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
        val businessWithUserId =
            business.copy(id = FirebaseAuth.getInstance().currentUser!!.email.toString())
        fireStore.collection(BUSINESS_COLLECTION).document(businessWithUserId.id)
            .set(businessWithUserId).await().toString()
    }

    override suspend fun update(business: Business): Unit = trace(UPDATE_BUSINESS_TRACE) {
        val businessId = business.id
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
    override suspend fun upLoadImage(businessId: String, image: Bitmap?) {


        fireStore.collection(BUSINESS_COLLECTION).document(businessId).update("image", image.toString())
            .await()
    }

    companion object {
        private const val CATEGORY_FIELD = "category"
        private const val USER_ID_FIELD = "userId"
        private const val BUSINESS_NAME = "name"
        private const val BUSINESS_COLLECTION = "businessAccount"
        private const val SAVE_BUSINESS_TRACE = "saveBusinessAccount"
        private const val UPDATE_BUSINESS_TRACE = "updateBusinessAccount"
    }
}