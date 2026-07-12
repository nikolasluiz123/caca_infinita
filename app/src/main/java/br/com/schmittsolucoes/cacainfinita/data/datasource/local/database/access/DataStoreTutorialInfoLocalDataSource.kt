package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tutorial_preferences")

class DataStoreTutorialInfoLocalDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) : TutorialInfoLocalDataSource {

    override fun isTutorialShown(tutorialId: String): Flow<Boolean> {
        val key = booleanPreferencesKey(tutorialId)
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    override suspend fun markTutorialAsShown(tutorialId: String) {
        val key = booleanPreferencesKey(tutorialId)
        context.dataStore.edit { preferences ->
            preferences[key] = true
        }
    }
}
