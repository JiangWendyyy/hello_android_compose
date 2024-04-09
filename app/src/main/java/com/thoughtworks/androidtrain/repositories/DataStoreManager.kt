package com.thoughtworks.androidtrain.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore("hintData")
private const val DATA_KEY = "hint"
class DataStoreManager( context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val hintKey = booleanPreferencesKey(DATA_KEY)
    }

    suspend fun setIsHintShown(isHintShown: Boolean) {
        dataStore.edit {
                preferences -> preferences[hintKey] = isHintShown
        }
    }

    fun getIsHintShown() : Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) { emit(emptyPreferences()) }
                else { throw exception }
            }
            .map { preferences -> preferences[hintKey] ?: false }
    }

}