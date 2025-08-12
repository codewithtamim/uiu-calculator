package io.github.codewithtamim.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TuitionPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val STUDENT_TYPE = stringPreferencesKey("student_type")
        val SCHOLARSHIP_PERCENT = intPreferencesKey("scholarship_percent")
        val TOTAL_AMOUNT = doublePreferencesKey("total_amount")
    }

    data class TuitionPrefs(
        val studentType: String = "Normal Student",
        val scholarshipPercent: Int = 0,
        val totalAmount: Double = 0.0
    )

    val preferencesFlow: Flow<TuitionPrefs> = dataStore.data.map { prefs ->
        TuitionPrefs(
            studentType = prefs[Keys.STUDENT_TYPE] ?: "Normal Student",
            scholarshipPercent = prefs[Keys.SCHOLARSHIP_PERCENT] ?: 0,
            totalAmount = prefs[Keys.TOTAL_AMOUNT] ?: 0.0
        )
    }

    suspend fun savePreferences(studentType: String, scholarshipPercent: Int, totalAmount: Double) {
        dataStore.edit { prefs ->
            prefs[Keys.STUDENT_TYPE] = studentType
            prefs[Keys.SCHOLARSHIP_PERCENT] = scholarshipPercent
            prefs[Keys.TOTAL_AMOUNT] = totalAmount
        }
    }
}


