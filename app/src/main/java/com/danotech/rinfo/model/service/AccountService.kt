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

package com.danotech.rinfo.model.service

import android.credentials.Credential
import com.danotech.rinfo.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthCredential
import kotlinx.coroutines.flow.Flow

/**
 * Service for managing user accounts.
 * this interface is used by the AccountServiceImpl class
 * @see AccountServiceImpl for implementation details
 */
interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>
    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun createAccountWithEmailAndPassword(email: String, password: String)
    suspend fun checkUserExistsByEmail(email: String): Boolean
    suspend fun linkAccount(email: String, password: String)
    suspend fun changePassword(oobCode: String, newPassword: String): Boolean
    suspend fun deleteAccount()
    suspend fun signInWithCredential(credential: AuthCredential): Boolean
    suspend fun signOut()
}
