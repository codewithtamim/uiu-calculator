package io.github.codewithtamim.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.codewithtamim.data.preferences.TuitionPreferencesRepository
import javax.inject.Singleton

private const val TUITION_PREFERENCES_NAME = "tuition_prefs"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TUITION_PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideTuitionPreferencesRepository(dataStore: DataStore<Preferences>): TuitionPreferencesRepository =
        TuitionPreferencesRepository(dataStore)
}


