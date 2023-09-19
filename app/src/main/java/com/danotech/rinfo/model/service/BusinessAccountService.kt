package com.danotech.rinfo.model.service

import android.graphics.Bitmap
import android.media.Image
import com.danotech.rinfo.model.Business
import kotlinx.coroutines.flow.Flow

interface BusinessAccountService {
    val currentUserBusinessAccount: Flow<List<Business>>
    suspend fun getAllBusiness(number: Int): Flow<List<Business>>
    suspend fun getBusinessByCategory(category: String): Flow<List<Business>>
    suspend fun getBusinessWhereLike(name: String): Flow<List<Business>>
    suspend fun getBusinessById(businessId: String): Business?
    suspend fun upLoadImage(businessId: String, image: String)
    suspend fun getBusinessByOwner(owner: String): Flow<List<Business>>
    suspend fun create(business: Business): String
    suspend fun update(business: Business)
    suspend fun delete(business: String)
}