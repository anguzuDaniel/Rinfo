package com.danotech.rinfo.model.service.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.danotech.rinfo.model.local.UserData
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")


@Singleton
class DataStoreUtil @Inject constructor(val context: Context) {
    val dataStore = context.dataStore

    companion object {
        val IS_DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val IS_LOGGED_KEY = booleanPreferencesKey("is_logged")
        val CARD_LAYOUT_INDEX = intPreferencesKey("card_layout_index")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        // Add more keys as needed to store user information

        // Function to save user data to DataStore
        suspend fun saveUserData(context: Context, email: String, userId: String) {
            context.dataStore.edit { preferences ->
                preferences[USER_EMAIL_KEY] = email
                preferences[USER_ID_KEY] = userId
                // Add more data as needed
            }
        }

        // Function to retrieve user data from DataStore
        suspend fun getUserData(context: Context): UserData? {
            val preferences = context.dataStore.data.first()
            val userEmail = preferences[USER_EMAIL_KEY] ?: return null
            val userId = preferences[USER_ID_KEY] ?: return null
            // Retrieve more data as needed

            return UserData(userEmail, userId) // Replace with your User data class
        }

        suspend fun saveCardLayout(context: Context, index: Int) {
            context.dataStore.edit { preferences ->
                preferences[CARD_LAYOUT_INDEX] = index
            }
        }
    }

    suspend fun getData(): Preferences {
        return dataStore.data.first().toPreferences()
    }

    fun store(): DataStore<Preferences> {
        return dataStore
    }

    suspend fun readUserId(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_ID_KEY]
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_EMAIL_KEY)
            // Remove other user-related data keys as needed.
        }
    }

    suspend fun isUserLoggedIn(context: Context): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[IS_LOGGED_KEY] ?: false
    }

    suspend fun saveDarkModePreference(context: Context, isDarkModeEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_KEY] = isDarkModeEnabled
        }
    }

    suspend fun clearAllUserData(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}