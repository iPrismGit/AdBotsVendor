package com.iprism.adbotsvendor.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iprism.adbotsvendor.data.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val TOKEN = stringPreferencesKey("token")
        val IS_LOGIN = booleanPreferencesKey("is_login")
    }

    suspend fun saveUser(userId: String, userName: String, token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[USER_NAME] = userName
            preferences[TOKEN] = token
        }
    }

    suspend fun loginUser() {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN] = true
        }
    }



    val userDetails: Flow<User> = context.dataStore.data.map { pref ->
        User(
            userId = pref[USER_ID],
            userName = pref[USER_NAME],
            token = pref[TOKEN],
            isLogin = pref[IS_LOGIN] ?: false
        )
    }

    suspend fun clearData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
