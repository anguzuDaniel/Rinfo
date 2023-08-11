package com.danotech.rinfo.model.service

import com.danotech.rinfo.model.BusinessDocument
import kotlinx.coroutines.flow.Flow

interface BusinessAccountService {
    val currentUserBusinessAccount: Flow<List<BusinessDocument>>

    suspend fun getAllBusiness(number: Int): Flow<List<BusinessDocument>>
    suspend fun getBusinessByCategory(category: String): Flow<List<BusinessDocument>>
    fun getBusinessWhereLike(name: String): Flow<List<BusinessDocument>>
    suspend fun getBusinessById(businessId: String): BusinessDocument?
    suspend fun getBusinessByOwner(owner: String): Flow<List<BusinessDocument>>
    suspend fun create(businessDocument: BusinessDocument): String
    suspend fun update(businessDocument: BusinessDocument)
    suspend fun delete(business: String)
}