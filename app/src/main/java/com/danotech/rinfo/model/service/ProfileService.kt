package com.danotech.rinfo.model.service

import com.danotech.rinfo.model.Profile
import kotlinx.coroutines.flow.Flow


interface ProfileService {
    val profiles: Flow<List<Profile>>

    suspend fun getProfile(profileId: String): Profile?
    suspend fun create(profile: Profile): String
    suspend fun update(profile: Profile)
    suspend fun delete(profileId: String)
}
